package com.extractors

import com.lagradost.cloudstream3.utils.ExtractorApi
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.utils.Qualities
import com.lagradost.cloudstream3.app

open class MediaFire : ExtractorApi() {
    override val name = "MediaFire"
    override val mainUrl = "https://mediafire.com"
    override val requiresReferer = false

    override suspend fun getUrl(url: String, referer: String?): List<ExtractorLink> {
        val sources = mutableListOf<ExtractorLink>()
        
        try {
            val doc = app.get(url).document
            
            // البحث عن زر التنزيل المباشر
            val downloadButton = doc.select("a[href*=.mp4], a[href*=.m3u8], .download_link, #downloadButton, .input[onclick*=.mp4]")
            
            if (downloadButton.isNotEmpty()) {
                val directLink = downloadButton.attr("href")
                
                if (directLink.isNotEmpty() && (directLink.contains(".mp4") || directLink.contains(".m3u8") || directLink.contains("mediafire"))) {
                    sources.add(
                        ExtractorLink(
                            name = name,
                            source = name,
                            url = directLink,
                            isM3u8 = directLink.contains(".m3u8"),
                            quality = Qualities.Unknown.value,
                            referer = mainUrl
                        )
                    )
                }
            }
            
            // البحث عن روابط في السكريبتات
            val scriptElements = doc.select("script")
            
            for (script in scriptElements) {
                val scriptData = script.data()
                
                if (scriptData.contains("mediafire") || scriptData.contains("download") || scriptData.contains("url")) {
                    try {
                        // استخراج الروابط باستخدام regex
                        val patterns = listOf(
                            Regex("""['"](https?://[^'"]*mediafire[^'"]*\.(mp4|m3u8)[^'"]*)['"]"""),
                            Regex("""['"](https?://download[^'"]*\.(mp4|m3u8)[^'"]*)['"]"""),
                            Regex("""window\.location\s*=\s*['"]([^'"]+)['"]"""),
                            Regex("""window\.open\(['"]([^'"]+)['"]"""),
                            Regex("""download\(['"]([^'"]+)['"]""")
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
                if (src.isNotEmpty() && (src.contains("mediafire") || src.contains(".mp4") || src.contains(".m3u8"))) {
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
            
            // البحث عن عناصر خاصة بالتنزيل
            val downloadElements = doc.select("[data-url], [data-link], [data-download], [onclick*=.mp4]")
            for (element in downloadElements) {
                val dataUrl = element.attr("data-url") + element.attr("data-link") + element.attr("data-download")
                val onclick = element.attr("onclick")
                
                val potentialUrl = if (dataUrl.isNotEmpty()) dataUrl else extractUrlFromOnclick(onclick)
                
                if (potentialUrl.isNotEmpty() && (potentialUrl.contains(".mp4") || potentialUrl.contains(".m3u8"))) {
                    sources.add(
                        ExtractorLink(
                            name = name,
                            source = name,
                            url = potentialUrl,
                            isM3u8 = potentialUrl.contains(".m3u8"),
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
    
    private fun extractUrlFromOnclick(onclick: String): String {
        if (onclick.isEmpty()) return ""
        
        // استخراج الرابط من onclick
        val patterns = listOf(
            Regex("""window\.location\.href\s*=\s*['"]([^'"]+)['"]"""),
            Regex("""window\.open\(['"]([^'"]+)['"]"""),
            Regex("""location\.href\s*=\s*['"]([^'"]+)['"]"""),
            Regex("""['"](https?://[^'"]+)['"]""")
        )
        
        for (pattern in patterns) {
            val match = pattern.find(onclick)
            if (match != null) {
                return match.groupValues[1]
            }
        }
        
        return ""
    }
}

// أنواع مختلفة من MediaFire
class MediaFireCom : MediaFire() {
    override val name = "MediaFire"
    override val mainUrl = "https://mediafire.com"
}