package com.extractors

import com.lagradost.cloudstream3.utils.ExtractorApi
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.utils.Qualities
import com.lagradost.cloudstream3.app

open class StreamTape : ExtractorApi() {
    override val name = "StreamTape"
    override val mainUrl = "https://streamtape.com"
    override val requiresReferer = false

    override suspend fun getUrl(url: String, referer: String?): List<ExtractorLink> {
        val sources = mutableListOf<ExtractorLink>()
        
        try {
            val doc = app.get(url).document
            
            // البحث عن رابط الفيديو في السكريبتات
            val scriptElements = doc.select("script")
            
            for (script in scriptElements) {
                val scriptData = script.data()
                
                // البحث عن البنية الخاصة بـ StreamTape
                if (scriptData.contains("videolink") || scriptData.contains("robotlink")) {
                    try {
                        // استخراج الرابط باستخدام regex
                        val linkPattern = Regex("""robotlink['"]\s*=\s*['"]([^'"]+)"""")
                        val linkMatch = linkPattern.find(scriptData)
                        
                        if (linkMatch != null) {
                            val videoLink = "https:" + linkMatch.groupValues[1]
                            
                            sources.add(
                                ExtractorLink(
                                    name = name,
                                    source = name,
                                    url = videoLink,
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
                if (src.isNotEmpty() && src.contains("streamtape")) {
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

// أنواع مختلفة من StreamTape
class StreamTapeNet : StreamTape() {
    override val name = "StreamTape"
    override val mainUrl = "https://streamtape.net"
}

class StreamTapeCom : StreamTape() {
    override val name = "StreamTape"
    override val mainUrl = "https://streamtape.com"
}

class StreamTapeTo : StreamTape() {
    override val name = "StreamTape"
    override val mainUrl = "https://streamtape.to"
}