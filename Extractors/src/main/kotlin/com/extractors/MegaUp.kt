package com.extractors

import com.lagradost.cloudstream3.utils.ExtractorApi
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.utils.Qualities
import com.lagradost.cloudstream3.app

open class MegaUp : ExtractorApi() {
    override val name = "MegaUp"
    override val mainUrl = "https://megaup.net"
    override val requiresReferer = false

    override suspend fun getUrl(url: String, referer: String?): List<ExtractorLink> {
        val sources = mutableListOf<ExtractorLink>()
        
        try {
            val doc = app.get(url).document
            
            // البحث عن رابط الفيديو في السكريبتات
            val scriptElements = doc.select("script")
            
            for (script in scriptElements) {
                val scriptData = script.data()
                
                // البحث عن البنية الخاصة بـ MegaUp
                if (scriptData.contains("megaupload") || scriptData.contains("download") || scriptData.contains("megaup")) {
                    try {
                        // استخراج الرابط باستخدام regex
                        val linkPatterns = listOf(
                            Regex("""['"](https?://[^'"]*megaup[^'"]*\.(mp4|m3u8)[^'"]*)['"]"""),
                            Regex("""['"](https?://[^'"]*download[^'"]*\.(mp4|m3u8)[^'"]*)['"]"""),
                            Regex("""download['"]\s*=\s*['"]([^'"]+)['"]"""),
                            Regex("""url['"]\s*=\s*['"]([^'"]+)['"]""")
                        )
                        
                        for (pattern in linkPatterns) {
                            val match = pattern.find(scriptData)
                            if (match != null) {
                                var videoLink = match.groupValues[1]
                                
                                // تنظيف وتصحيح الرابط
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
                                return sources
                            }
                        }
                    } catch (e: Exception) {
                        continue
                    }
                }
            }
            
            // البحث عن عناصر الفيديو المباشرة
            val videoElements = doc.select("video source, video")
            for (element in videoElements) {
                val src = element.attr("src")
                if (src.isNotEmpty() && (src.contains("megaup") || src.contains("download") || src.contains("mp4"))) {
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
            
            // البحث عن روابط التحميل المباشرة
            val downloadLinks = doc.select("a[href*=.mp4], a[href*=.m3u8], a[href*=download]")
            for (link in downloadLinks) {
                val href = link.attr("href")
                if (href.isNotEmpty()) {
                    var fullUrl = href
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
            // يمكن إضافة logging هنا
        }
        
        return sources
    }
}

// أنواع مختلفة من MegaUp
class MegaUpCo : MegaUp() {
    override val name = "MegaUp"
    override val mainUrl = "https://megaup.co"
}

class MegaUpIo : MegaUp() {
    override val name = "MegaUp"
    override val mainUrl = "https://megaup.io"
}