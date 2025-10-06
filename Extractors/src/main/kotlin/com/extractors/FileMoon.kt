package com.extractors

import com.lagradost.cloudstream3.utils.ExtractorApi
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.utils.Qualities
import com.lagradost.cloudstream3.app

open class FileMoon : ExtractorApi() {
    override val name = "FileMoon"
    override val mainUrl = "https://filemoon.sx"
    override val requiresReferer = false

    override suspend fun getUrl(url: String, referer: String?): List<ExtractorLink> {
        val sources = mutableListOf<ExtractorLink>()
        
        try {
            val doc = app.get(url).document
            
            // البحث عن روابط M3U8
            val scriptElements = doc.select("script")
            
            for (script in scriptElements) {
                val scriptData = script.data()
                
                // البحث عن روابط M3U8
                if (scriptData.contains(".m3u8") || scriptData.contains("hls") || scriptData.contains("filemoon")) {
                    try {
                        // استخراج روابط M3U8
                        val m3u8Pattern = Regex("""['"]([^'"]*\.m3u8[^'"]*)['"]"""")
                        val m3u8Matches = m3u8Pattern.findAll(scriptData)
                        
                        for (match in m3u8Matches) {
                            var m3u8Url = match.groupValues[1]
                            
                            // تصحيح الرابط
                            if (!m3u8Url.startsWith("http") && m3u8Url.startsWith("//")) {
                                m3u8Url = "https:$m3u8Url"
                            } else if (!m3u8Url.startsWith("http")) {
                                m3u8Url = "https://$m3u8Url"
                            }
                            
                            sources.add(
                                ExtractorLink(
                                    name = name,
                                    source = name,
                                    url = m3u8Url,
                                    isM3u8 = true,
                                    quality = Qualities.Unknown.value,
                                    referer = mainUrl
                                )
                            )
                        }
                        
                        // استخراج روابط مباشرة
                        val mp4Pattern = Regex("""['"]([^'"]*\.mp4[^'"]*)['"]"""")
                        val mp4Matches = mp4Pattern.findAll(scriptData)
                        
                        for (match in mp4Matches) {
                            var mp4Url = match.groupValues[1]
                            
                            // تصحيح الرابط
                            if (!mp4Url.startsWith("http") && mp4Url.startsWith("//")) {
                                mp4Url = "https:$mp4Url"
                            } else if (!mp4Url.startsWith("http")) {
                                mp4Url = "https://$mp4Url"
                            }
                            
                            sources.add(
                                ExtractorLink(
                                    name = name,
                                    source = name,
                                    url = mp4Url,
                                    isM3u8 = false,
                                    quality = Qualities.Unknown.value,
                                    referer = mainUrl
                                )
                            )
                        }
                        
                        // البحث عن روابط مع جودة محددة
                        val qualityPattern = Regex("""['"]([^'"]*filemoon[^'"]*(?:1080|720|480|360)[^'"]*)['"]"""")
                        val qualityMatches = qualityPattern.findAll(scriptData)
                        
                        for (match in qualityMatches) {
                            var qualityUrl = match.groupValues[1]
                            
                            // تصحيح الرابط
                            if (!qualityUrl.startsWith("http") && qualityUrl.startsWith("//")) {
                                qualityUrl = "https:$qualityUrl"
                            } else if (!qualityUrl.startsWith("http")) {
                                qualityUrl = "https://$qualityUrl"
                            }
                            
                            // تحديد الجودة
                            val quality = when {
                                qualityUrl.contains("1080") -> Qualities.P1080.value
                                qualityUrl.contains("720") -> Qualities.P720.value
                                qualityUrl.contains("480") -> Qualities.P480.value
                                qualityUrl.contains("360") -> Qualities.P360.value
                                else -> Qualities.Unknown.value
                            }
                            
                            sources.add(
                                ExtractorLink(
                                    name = name,
                                    source = name,
                                    url = qualityUrl,
                                    isM3u8 = qualityUrl.contains(".m3u8"),
                                    quality = quality,
                                    referer = mainUrl
                                )
                            )
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
                if (src.isNotEmpty() && (src.contains("filemoon") || src.contains(".m3u8") || src.contains(".mp4"))) {
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

// أنواع مختلفة من FileMoon
class FileMoonTo : FileMoon() {
    override val name = "FileMoon"
    override val mainUrl = "https://filemoon.to"
}

class FileMoonIn : FileMoon() {
    override val name = "FileMoon"
    override val mainUrl = "https://filemoon.in"
}

class FileMoonNl : FileMoon() {
    override val name = "FileMoon"
    override val mainUrl = "https://filemoon.nl"
}