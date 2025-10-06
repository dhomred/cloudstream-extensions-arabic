package com.extractors

import com.lagradost.cloudstream3.utils.ExtractorApi
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.utils.Qualities
import com.lagradost.cloudstream3.app

open class MixDrop : ExtractorApi() {
    override val name = "MixDrop"
    override val mainUrl = "https://mixdrop.co"
    override val requiresReferer = false

    override suspend fun getUrl(url: String, referer: String?): List<ExtractorLink> {
        val sources = mutableListOf<ExtractorLink>()
        
        try {
            val doc = app.get(url).document
            
            // البحث عن رابط الفيديو في السكريبتات
            val scriptElements = doc.select("script")
            
            for (script in scriptElements) {
                val scriptData = script.data()
                
                // البحث عن البنية الخاصة بـ MixDrop
                if (scriptData.contains("MDCore") || scriptData.contains("wurl") || scriptData.contains("mixdrop")) {
                    try {
                        // استخراج الرابط باستخدام regex
                        val wurlPattern = Regex("""wurl\s*=\s*['"]([^'"]+)['"]"""")
                        val wurlMatch = wurlPattern.find(scriptData)
                        
                        if (wurlMatch != null) {
                            val wurl = wurlMatch.groupValues[1]
                            val videoLink = if (wurl.startsWith("http")) wurl else "https:$wurl"
                            
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
                if (src.isNotEmpty() && (src.contains("mixdrop") || src.contains("mp4"))) {
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
            
            // البحث عن روابط في عناصر خاصة
            val specialElements = doc.select("[data-url], [data-link]")
            for (element in specialElements) {
                val dataUrl = element.attr("data-url")
                val dataLink = element.attr("data-link")
                val link = dataUrl.ifEmpty { dataLink }
                
                if (link.isNotEmpty() && (link.contains("mixdrop") || link.contains(".mp4"))) {
                    sources.add(
                        ExtractorLink(
                            name = name,
                            source = name,
                            url = link,
                            isM3u8 = link.contains(".m3u8"),
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

// أنواع مختلفة من MixDrop
class MixDropAg : MixDrop() {
    override val name = "MixDrop"
    override val mainUrl = "https://mixdrop.ag"
}

class MixDropTo : MixDrop() {
    override val name = "MixDrop"
    override val mainUrl = "https://mixdrop.to"
}

class MixDropCh : MixDrop() {
    override val name = "MixDrop"
    override val mainUrl = "https://mixdrop.ch"
}