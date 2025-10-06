package com.extractors

import com.lagradost.cloudstream3.utils.ExtractorApi
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.utils.Qualities
import com.lagradost.cloudstream3.app

open class BayFiles : ExtractorApi() {
    override val name = "BayFiles"
    override val mainUrl = "https://bayfiles.com"
    override val requiresReferer = false

    override suspend fun getUrl(url: String, referer: String?): List<ExtractorLink> {
        val sources = mutableListOf<ExtractorLink>()
        
        try {
            val doc = app.get(url).document
            
            // استخراج المعلومات الأساسية
            val title = doc.select("title").text()
            
            // البحث عن روابط التنزيل المباشرة
            val downloadElements = doc.select("a[href*=.mp4], a[href*=.m3u8], a[download], .download-link")
            
            for (element in downloadElements) {
                val downloadUrl = element.attr("href")
                
                if (downloadUrl.isNotEmpty() && (downloadUrl.contains(".mp4") || downloadUrl.contains(".m3u8") || downloadUrl.contains("bayfiles"))) {
                    var fullUrl = downloadUrl
                    
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
            
            // البحث في السكريبتات عن روابط مباشرة
            val scriptElements = doc.select("script")
            
            for (script in scriptElements) {
                val scriptData = script.data()
                
                if (scriptData.contains("bayfiles") || scriptData.contains("download") || scriptData.contains("url")) {
                    try {
                        // استخراج الروابط باستخدام regex
                        val patterns = listOf(
                            Regex("""['"](https?://[^'"]*bayfiles[^'"]*\.(mp4|m3u8)[^'"]*)['"]"""),
                            Regex("""['"](https?://[^'"]*download[^'"]*\.(mp4|m3u8)[^'"]*)['"]"""),
                            Regex("""['"](https?://[^'"]*cdn[^'"]*\.(mp4|m3u8)[^'"]*)['"]"""),
                            Regex("""url['"]\s*=\s*['"]([^'"]+)['"]""")
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
                if (src.isNotEmpty() && (src.contains("bayfiles") || src.contains(".mp4") || src.contains(".m3u8"))) {
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
            
            // البحث عن عناصر خاصة بالروابط
            val linkElements = doc.select("[data-url], [data-link], [data-download]")
            for (element in linkElements) {
                val dataUrl = element.attr("data-url") + element.attr("data-link") + element.attr("data-download")
                if (dataUrl.isNotEmpty() && (dataUrl.contains(".mp4") || dataUrl.contains(".m3u8"))) {
                    sources.add(
                        ExtractorLink(
                            name = name,
                            source = name,
                            url = dataUrl,
                            isM3u8 = dataUrl.contains(".m3u8"),
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

// أنواع مختلفة من BayFiles
class BayFilesCom : BayFiles() {
    override val name = "BayFiles"
    override val mainUrl = "https://bayfiles.com"
}

class BayFilesVn : BayFiles() {
    override val name = "BayFiles"
    override val mainUrl = "https://bayfiles.vn"
}