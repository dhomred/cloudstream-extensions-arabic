package com.extractors

import com.lagradost.cloudstream3.utils.ExtractorApi
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.utils.Qualities
import com.lagradost.cloudstream3.app

open class DoodStream : ExtractorApi() {
    override val name = "DoodStream"
    override val mainUrl = "https://doodstream.com"
    override val requiresReferer = false

    override suspend fun getUrl(url: String, referer: String?): List<ExtractorLink> {
        val sources = mutableListOf<ExtractorLink>()
        
        try {
            val doc = app.get(url).document
            
            // البحث عن رابط الفيديو في السكريبتات
            val scriptElements = doc.select("script")
            
            for (script in scriptElements) {
                val scriptData = script.data()
                
                // البحث عن البنية الخاصة بـ DoodStream
                if (scriptData.contains("dsplayer") || scriptData.contains("dood")) {
                    try {
                        // استخراج الرابط باستخدام regex
                        val linkPattern = Regex("""dsplayer['"]\s*=\s*['"]([^'"]+)"""")
                        val linkMatch = linkPattern.find(scriptData)
                        
                        if (linkMatch != null) {
                            val videoLink = linkMatch.groupValues[1]
                            
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
                        
                        // محاولة أخرى للبحث عن رابط مباشر
                        val directLinkPattern = Regex("""['"](https?://[^'"]+\.mp4[^'"]*)['"]"""")
                        val directMatch = directLinkPattern.find(scriptData)
                        
                        if (directMatch != null) {
                            val directLink = directMatch.groupValues[1]
                            
                            sources.add(
                                ExtractorLink(
                                    name = name,
                                    source = name,
                                    url = directLink,
                                    isM3u8 = false,
                                    quality = Qualities.Unknown.value,
                                    referer = mainUrl
                                )
                            )
                            return sources
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
                if (src.isNotEmpty() && (src.contains("dood") || src.contains("mp4"))) {
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
            // يمكن إضافة logging هنا
        }
        
        return sources
    }
}

// أنواع مختلفة من DoodStream
class DoodWs : DoodStream() {
    override val name = "DoodStream"
    override val mainUrl = "https://dood.ws"
}

class DoodTo : DoodStream() {
    override val name = "DoodStream"
    override val mainUrl = "https://dood.to"
}

class DoodWatch : DoodStream() {
    override val name = "DoodStream"
    override val mainUrl = "https://dood.watch"
}