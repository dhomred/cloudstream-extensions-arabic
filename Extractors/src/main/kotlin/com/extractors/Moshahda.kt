package com.extractors

import com.lagradost.cloudstream3.utils.ExtractorApi
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.utils.Qualities
import com.lagradost.cloudstream3.app

open class Moshahda : ExtractorApi() {
    override val name = "Moshahda"
    override val mainUrl = "https://moshahda.net"
    override val requiresReferer = true

    override suspend fun getUrl(url: String, referer: String?): List<ExtractorLink> {
        val sources = mutableListOf<ExtractorLink>()
        
        try {
            val doc = app.get(url, referer = referer).document
            
            // البحث عن روابط التحميل المباشرة
            val downloadLinks = mapOf(
                "download_l" to 240,
                "download_n" to 360,
                "download_h" to 480,
                "download_x" to 720,
                "download_o" to 1080
            )
            
            // استخراج الكود من الرابط
            val regcode = """$mainUrl/embed-(\w+)""".toRegex()
            val code = regcode.find(url)?.groupValues?.getOrNull(1)
            
            if (code != null) {
                val baseLink = "$mainUrl/$code.html?"
                
                // إضافة روابط التحميل المختلفة
                downloadLinks.forEach { (key, quality) ->
                    val downloadUrl = baseLink + key
                    sources.add(
                        ExtractorLink(
                            name = name,
                            source = name,
                            url = downloadUrl,
                            isM3u8 = false,
                            quality = quality,
                            referer = referer ?: mainUrl
                        )
                    )
                }
            }
            
            // البحث عن روابط في السكريبتات
            val scriptElements = doc.select("script")
            for (script in scriptElements) {
                val scriptData = script.data()
                
                // البحث عن روابط مباشرة
                val directLinkPattern = Regex("""["'](https?://[^"']+\.(?:mp4|m3u8))["']"""")
                val matches = directLinkPattern.findAll(scriptData)
                
                matches.forEach { match ->
                    val videoUrl = match.groupValues[1]
                    sources.add(
                        ExtractorLink(
                            name = name,
                            source = name,
                            url = videoUrl,
                            isM3u8 = videoUrl.contains(".m3u8"),
                            quality = Qualities.Unknown.value,
                            referer = referer ?: mainUrl
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