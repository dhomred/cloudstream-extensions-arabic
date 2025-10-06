package com.extractors

import com.lagradost.cloudstream3.utils.ExtractorApi
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.utils.Qualities
import com.lagradost.cloudstream3.app

open class Fichier : ExtractorApi() {
    override val name = "1Fichier"
    override val mainUrl = "https://1fichier.com"
    override val requiresReferer = false

    override suspend fun getUrl(url: String, referer: String?): List<ExtractorLink> {
        val sources = mutableListOf<ExtractorLink>()
        
        try {
            val doc = app.get(url).document
            
            // استخراج معلومات الملف
            val fileName = doc.select(".file-name, .filename, h1").text()
            val fileSize = doc.select(".file-size, .size").text()
            
            // البحث عن زر التنزيل المباشر
            val downloadButtons = doc.select("input[type=submit], button[type=submit], .download-button, .btn-download")
            
            // إذا وجدنا زر تنزيل، نحاول إرسال النموذج
            if (downloadButtons.isNotEmpty()) {
                try {
                    // استخراج معلومات النموذج
                    val form = doc.select("form").first()
                    val action = form?.attr("action") ?: url
                    
                    // إنشاء طلب POST
                    val formData = mutableMapOf<String, String>()
                    
                    // استخراج حقول الإدخال المخفية
                    val hiddenInputs = form?.select("input[type=hidden]") ?: emptyList()
                    for (input in hiddenInputs) {
                        val name = input.attr("name")
                        val value = input.attr("value")
                        if (name.isNotEmpty()) {
                            formData[name] = value
                        }
                    }
                    
                    // إضافة حقل الإرسال
                    formData["submit"] = "download"
                    
                    // إرسال النموذج والحصول على الرد
                    val response = app.post(action, data = formData)
                    val responseDoc = response.document
                    
                    // البحث عن روابط التنزيل في الرد
                    val downloadLinks = responseDoc.select("a[href*=.mp4], a[href*=.m3u8], a[download]")
                    
                    for (link in downloadLinks) {
                        val href = link.attr("href")
                        
                        if (href.isNotEmpty() && (href.contains(".mp4") || href.contains(".m3u8") || href.contains("1fichier"))) {
                            var fullUrl = href
                            
                            // تصحيح الرابط
                            if (!fullUrl.startsWith("http") && fullUrl.startsWith("/")) {
                                fullUrl = mainUrl + fullUrl
                            } else if (!fullUrl.startsWith("http")) {
                                fullUrl = "https://$fullUrl"
                            }
                            
                            sources.add(
                                ExtractorLink(
                                    name = name,
                                    source = name,
                                    url = fullUrl,
                                    isM3u8 = fullUrl.contains(".m3u8"),
                                    quality = Qualities.Unknown.value,
                                    referer = mainUrl
                                )
                            )
                        }
                    }
                    
                } catch (e: Exception) {
                    // تجاهل أخطاء النموذج
                }
            }
            
            // البحث عن روابط مباشرة في الصفحة
            val directLinks = doc.select("a[href*=.mp4], a[href*=.m3u8], a[download], .direct-link")
            
            for (link in directLinks) {
                val href = link.attr("href")
                
                if (href.isNotEmpty() && (href.contains(".mp4") || href.contains(".m3u8") || href.contains("1fichier"))) {
                    var fullUrl = href
                    
                    // تصحيح الرابط
                    if (!fullUrl.startsWith("http") && fullUrl.startsWith("/")) {
                        fullUrl = mainUrl + fullUrl
                    } else if (!fullUrl.startsWith("http")) {
                        fullUrl = "https://$fullUrl"
                    }
                    
                    sources.add(
                        ExtractorLink(
                            name = name,
                            source = name,
                            url = fullUrl,
                            isM3u8 = fullUrl.contains(".m3u8"),
                            quality = Qualities.Unknown.value,
                            referer = mainUrl
                        )
                    )
                }
            }
            
            // البحث في السكريبتات
            val scriptElements = doc.select("script")
            
            for (script in scriptElements) {
                val scriptData = script.data()
                
                if (scriptData.contains("1fichier") || scriptData.contains("download") || scriptData.contains("url")) {
                    try {
                        // استخراج الروابط باستخدام regex
                        val patterns = listOf(
                            Regex("""['"](https?://[^'"]*1fichier[^'"]*\.(mp4|m3u8)[^'"]*)['"]"""),
                            Regex("""['"](https?://[^'"]*download[^'"]*\.(mp4|m3u8)[^'"]*)['"]"""),
                            Regex("""['"](https?://[^'"]*cdn[^'"]*\.(mp4|m3u8)[^'"]*)['"]"""),
                            Regex("""window\.open\(['"]([^'"]+)['"]"""),
                            Regex("""location\.href\s*=\s*['"]([^'"]+)['"]""")
                        )
                        
                        for (pattern in patterns) {
                            val matches = pattern.findAll(scriptData)
                            
                            for (match in matches) {
                                var videoLink = match.groupValues[1]
                                
                                // تصحيح الرابط
                                if (!videoLink.startsWith("http") && videoLink.startsWith("//")) {
                                    videoLink = "https:$videoLink"
                                } else if (!videoLink.startsWith("http")) {
                                    videoLink = "https://$videoLink"
                                }
                                
                                sources.add(
                                    ExtractorLink(
                                        name = name,
                                        source = name,
                                        url = videoLink,
                                        isM3u8 = videoLink.contains(".m3u8"),
                                        quality = Qualities.Unknown.value,
                                        referer = mainUrl
                                    )
                                )
                            }
                        }
                    } catch (e: Exception) {
                        continue
                    }
                }
            }
            
            // البحث عن عناصر الفيديو
            val videoElements = doc.select("video source, video")
            for (element in videoElements) {
                val src = element.attr("src")
                if (src.isNotEmpty() && (src.contains("1fichier") || src.contains(".mp4") || src.contains(".m3u8"))) {
                    sources.add(
                        ExtractorLink(
                            name = name,
                            source = name,
                            url = src,
                            isM3u8 = src.contains(".m3u8"),
                            quality = Qualities.Unknown.value,
                            referer = mainUrl
                        )
                    )
                }
            }
            
        } catch (e: Exception) {
            // معالجة الأخطاء
        }
        
        return sources
    }
}

// أنواع مختلفة من 1Fichier
class FichierCom : Fichier() {
    override val name = "1Fichier"
    override val mainUrl = "https://1fichier.com"
}

class FichierOrg : Fichier() {
    override val name = "1Fichier"
    override val mainUrl = "https://1fichier.org"
}