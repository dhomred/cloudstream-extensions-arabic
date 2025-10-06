package com.extractors

import com.lagradost.cloudstream3.utils.ExtractorApi
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.utils.Qualities
import com.lagradost.cloudstream3.app

open class Zippyshare : ExtractorApi() {
    override val name = "Zippyshare"
    override val mainUrl = "https://zippyshare.com"
    override val requiresReferer = false

    override suspend fun getUrl(url: String, referer: String?): List<ExtractorLink> {
        val sources = mutableListOf<ExtractorLink>()
        
        try {
            val doc = app.get(url).document
            
            // استخراج ID الملف من الرابط
            val fileId = extractFileId(url)
            
            // البحث عن روابط التنزيل المباشرة
            val downloadLinks = doc.select("a[href*=.mp4], a[href*=.m3u8], #dlbutton, .download, .dlbutton")
            
            for (link in downloadLinks) {
                var href = link.attr("href")
                
                if (href.isNotEmpty() && (href.contains(".mp4") || href.contains(".m3u8") || href.contains("zippyshare"))) {
                    // تصحيح الرابط
                    if (!href.startsWith("http")) {
                        href = if (href.startsWith("//")) "https:$href" else "https://zippyshare.com$href"
                    }
                    
                    sources.add(
                        ExtractorLink(
                            name = name,
                            source = name,
                            url = href,
                            isM3u8 = href.contains(".m3u8"),
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
                
                if (scriptData.contains("download") || scriptData.contains("url") || scriptData.contains("mp4") || scriptData.contains("m3u8")) {
                    try {
                        // استخراج الروابط باستخدام regex
                        val patterns = listOf(
                            Regex("""['"](https?://[^'"]*zippyshare[^'"]*\.(mp4|m3u8)[^'"]*)['"]"""),
                            Regex("""['"](https?://[^'"]*download[^'"]*\.(mp4|m3u8)[^'"]*)['"]"""),
                            Regex("""window\.location\s*=\s*['"]([^'"]+)['"]"""),
                            Regex("""window\.open\(['"]([^'"]+)['"]"""),
                            Regex("""download\(['"]([^'"]+)['"]"""),
                            Regex("""url\s*[:=]\s*['"]([^'"]+)['"]"""),
                            Regex("""href\s*[:=]\s*['"]([^'"]+)['"]""")
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
                                
                                // التحقق من أن الرابط يحتوي على zippyshare أو امتداد الفيديو
                                if (videoLink.contains("zippyshare") || videoLink.contains(".mp4") || videoLink.contains(".m3u8")) {
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
                        }
                    } catch (e: Exception) {
                        continue
                    }
                }
            }
            
            // محاولة بناء رابط التنزيل باستخدام fileId
            if (fileId != null) {
                val constructedUrl = "https://www.zippyshare.com/d/$fileId"
                sources.add(
                    ExtractorLink(
                        name = name,
                        source = name,
                        url = constructedUrl,
                        isM3u8 = false,
                        quality = Qualities.Unknown.value,
                        referer = mainUrl
                    )
                )
            }
            
            // البحث عن عناصر الفيديو
            val videoElements = doc.select("video source, video")
            for (element in videoElements) {
                val src = element.attr("src")
                if (src.isNotEmpty() && (src.contains(".mp4") || src.contains(".m3u8"))) {
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
    
    private fun extractFileId(url: String): String? {
        val patterns = listOf(
            Regex("""zippyshare\.com/v/([a-zA-Z0-9]+)"""),
            Regex("""zippyshare\.com/d/([a-zA-Z0-9]+)"""),
            Regex("""/([a-zA-Z0-9]{8})/""")
        )
        
        for (pattern in patterns) {
            val match = pattern.find(url)
            if (match != null) {
                return match.groupValues[1]
            }
        }
        
        return null
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

// أنواع مختلفة من Zippyshare
class ZippyshareCom : Zippyshare() {
    override val name = "Zippyshare"
    override val mainUrl = "https://zippyshare.com"
}