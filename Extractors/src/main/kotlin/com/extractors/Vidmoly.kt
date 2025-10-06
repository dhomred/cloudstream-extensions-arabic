package com.extractors

import com.lagradost.cloudstream3.utils.ExtractorApi
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.app
import com.lagradost.cloudstream3.utils.Qualities

open class Vidmoly : ExtractorApi() {
    override val name = "Vidmoly"
    override val mainUrl = "https://vidmoly.to"
    override val requiresReferer = false

    override suspend fun getUrl(url: String, referer: String?): List<ExtractorLink> {
        val doc = app.get(url).document
        val scriptElements = doc.select("script")
        
        // البحث عن رابط m3u8 في جميع السكريبتات
        for (script in scriptElements) {
            val scriptData = script.data()
            if (scriptData.contains("sources")) {
                try {
                    // محاولة استخراج رابط m3u8 بعدة طرق
                    val m3u8Pattern = Regex("""sources:\s*\[\s*\{\s*file:\s*["']([^"']+)"""")
                    val match = m3u8Pattern.find(scriptData)
                    
                    if (match != null) {
                        val m3u8Url = match.groupValues[1]
                        return listOf(ExtractorLink(
                            this.name,
                            this.name,
                            m3u8Url,
                            referer ?: mainUrl,
                            Qualities.Unknown.value,
                            m3u8Url.contains(".m3u8")
                        ))
                    }
                } catch (e: Exception) {
                    // تجاهل الأخطاء والاستمرار
                }
            }
        }
        
        // إذا لم يتم العثور على m3u8، البحث عن روابط مباشرة
        val videoElements = doc.select("video source")
        for (element in videoElements) {
            val src = element.attr("src")
            if (src.isNotEmpty()) {
                return listOf(ExtractorLink(
                    this.name,
                    this.name,
                    src,
                    referer ?: mainUrl,
                    Qualities.Unknown.value,
                    src.contains(".m3u8") || src.contains(".mp4")
                ))
            }
        }
        
        return emptyList()
    }
}