package com.extractors

import com.lagradost.cloudstream3.utils.ExtractorApi
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.utils.Qualities
import com.lagradost.cloudstream3.app

open class Uptobox : ExtractorApi() {
    override val name = "Uptobox"
    override val mainUrl = "https://uptobox.com"
    override val requiresReferer = false

    override suspend fun getUrl(url: String, referer: String?): List<ExtractorLink> {
        val sources = mutableListOf<ExtractorLink>()
        
        try {
            val doc = app.get(url).document
            
            // استخراج ID الملف من الرابط
            val fileId = extractFileId(url)
            
            // البحث عن روابط التنزيل المباشرة
            val directLinks = doc.select("a[href*=.mp4], a[href*=.m3u8], a[download], .direct-link")
            
            for (link in directLinks) {
                val href = link.attr("href")
                
                if (href.isNotEmpty() && (href.contains(".mp4") || href.contains(".m3u8") || href.contains("uptobox"))) {
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
            
            // إذا لم نجد روابط مباشرة، نحاول إنشاء رابط API
            if (fileId.isNotEmpty() && sources.isEmpty()) {
                val apiUrl = "https://uptobox.com/api/streaming?file_code=$fileId"
                
                try {
                    val apiDoc = app.get(apiUrl).document
                    val jsonData = apiData.text()
                    
                    // استخراج الروابط من JSON
                    val patterns = listOf(
                        Regex(""""stream_url"\s*:\s*"([^"]+)""""),
                        Regex(""""download_url"\s*:\s*"([^"]+)""""),
                        Regex(""""video_url"\s*:\s*"([^"]+)"""")
                    )
                    
                    for (pattern in patterns) {
                        val matches = pattern.findAll(jsonData)
                        for (match in matches) {
                            val videoUrl = match.groupValues[1]
                            
                            sources.add(
                                ExtractorLink(
                                    name = name,
                                    source = name,
                                    url = videoUrl,
                                    isM3u8 = videoUrl.contains(".m3u8"),
                                    quality = Qualities.Unknown.value,
                                    referer = mainUrl
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    // تجاهل أخطاء API
                }
            }
            
            // البحث في السكريبتات
            val scriptElements = doc.select("script")
            
            for (script in scriptElements) {
                val scriptData = script.data()
                
                if (scriptData.contains("uptobox") || scriptData.contains("stream") || scriptData.contains("url")) {
                    try {
                        // استخراج الروابط باستخدام regex
                        val patterns = listOf(
                            Regex("""['"](https?://[^'"]*uptobox[^'"]*\.(mp4|m3u8)[^'"]*)['"]"""),
                            Regex("""['"](https?://[^'"]*stream[^'"]*\.(mp4|m3u8)[^'"]*)['"]"""),
                            Regex("""url['"]\s*=\s*['"]([^'"]+)['"]"""),
                            Regex("""window\.location\.href\s*=\s*['"]([^'"]+)['"]""")
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
                if (src.isNotEmpty() && (src.contains("uptobox") || src.contains(".mp4") || src.contains(".m3u8"))) {
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
    
    private fun extractFileId(url: String): String {
        // استخراج ID من الرابط
        val patterns = listOf(
            Regex("""uptobox\.com/([a-zA-Z0-9]+)"""),
            Regex("""uptobox\.com/api/streaming\?file_code=([a-zA-Z0-9]+)"""),
            Regex("""([a-zA-Z0-9]{8,})""")
        )
        
        for (pattern in patterns) {
            val match = pattern.find(url)
            if (match != null) {
                return match.groupValues[1]
            }
        }
        
        return ""
    }
}

// أنواع مختلفة من Uptobox
class UptoboxCom : Uptobox() {
    override val name = "Uptobox"
    override val mainUrl = "https://uptobox.com"
}

class UptoboxNl : Uptobox() {
    override val name = "Uptobox"
    override val mainUrl = "https://uptobox.nl"
}