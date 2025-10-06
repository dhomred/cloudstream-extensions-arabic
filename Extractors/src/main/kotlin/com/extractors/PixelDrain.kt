package com.extractors

import com.lagradost.cloudstream3.utils.ExtractorApi
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.utils.Qualities
import com.lagradost.cloudstream3.app

open class PixelDrain : ExtractorApi() {
    override val name = "PixelDrain"
    override val mainUrl = "https://pixeldrain.com"
    override val requiresReferer = false

    override suspend fun getUrl(url: String, referer: String?): List<ExtractorLink> {
        val sources = mutableListOf<ExtractorLink>()
        
        try {
            val doc = app.get(url).document
            
            // استخراج ID الملف من الرابط
            val fileId = extractFileId(url)
            
            if (fileId.isNotEmpty()) {
                // إنشاء رابط التنزيل المباشر
                val downloadUrl = "https://pixeldrain.com/api/file/$fileId"
                
                sources.add(
                    ExtractorLink(
                        name = name,
                        source = name,
                        url = downloadUrl,
                        isM3u8 = false,
                        quality = Qualities.Unknown.value,
                        referer = mainUrl
                    )
                )
            }
            
            // البحث عن روابط بديلة
            val scriptElements = doc.select("script")
            for (script in scriptElements) {
                val scriptData = script.data()
                
                if (scriptData.contains("pixeldrain") || scriptData.contains("download")) {
                    // استخراج الروابط باستخدام regex
                    val linkPattern = Regex("""['"](https?://[^'"]*pixeldrain[^'"]*\.(mp4|m3u8)[^'"]*)['"]"""")
                    val matches = linkPattern.findAll(scriptData)
                    
                    for (match in matches) {
                        val videoLink = match.groupValues[1]
                        
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
            // معالجة الأخطاء
        }
        
        return sources
    }
    
    private fun extractFileId(url: String): String {
        // استخراج ID من الرابط
        val patterns = listOf(
            Regex("""pixeldrain\.com/u/([a-zA-Z0-9]+)"""),
            Regex("""pixeldrain\.com/api/file/([a-zA-Z0-9]+)"""),
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

// أنواع مختلفة من PixelDrain
class PixelDrainCom : PixelDrain() {
    override val name = "PixelDrain"
    override val mainUrl = "https://pixeldrain.com"
}