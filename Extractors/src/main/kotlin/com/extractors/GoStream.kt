package com.extractors

import com.lagradost.cloudstream3.utils.ExtractorApi
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.utils.Qualities
import com.lagradost.cloudstream3.app

open class GoStream : ExtractorApi() {
    override val name = "GoStream"
    override val mainUrl = "https://gostream.pro"
    override val requiresReferer = false

    override suspend fun getUrl(url: String, referer: String?): List<ExtractorLink> {
        val sources = mutableListOf<ExtractorLink>()
        
        try {
            val doc = app.get(url).document
            
            // محاولة استخراج السكريبتات المختلفة
            val scriptElements = doc.select("script")
            
            for (script in scriptElements) {
                val scriptData = script.data()
                
                // البحث عن البنية التقليدية
                if (scriptData.contains("sources") && scriptData.contains("file")) {
                    try {
                        val filePattern = Regex("""file:\s*["']([^"']+)"""")
                        val fileMatch = filePattern.find(scriptData)
                        
                        if (fileMatch != null) {
                            val videoUrl = fileMatch.groupValues[1]
                            sources.add(
                                ExtractorLink(
                                    name = name,
                                    source = name,
                                    url = videoUrl,
                                    isM3u8 = videoUrl.contains(".m3u8"),
                                    quality = Qualities.Unknown.value,
                                    referer = "$mainUrl/"
                                )
                            )
                            return sources
                        }
                    } catch (e: Exception) {
                        continue
                    }
                }
                
                // البحث عن بنية التشفير التقليدية
                if (scriptData.contains("|")) {
                    try {
                        val parts = scriptData.split("|")
                        if (parts.size >= 12) {
                            val b = parts[0].substringAfterLast("http")
                            val link = "$b://${parts[5]}.${parts[4]}-${parts[3]}.${parts[2]}:${parts[11]}/d/${parts[10]}/${parts[9]}.${parts[8]}"
                            
                            if (link.isNotBlank() && link.startsWith("http")) {
                                sources.add(
                                    ExtractorLink(
                                        name = name,
                                        source = name,
                                        url = link,
                                        isM3u8 = false,
                                        quality = Qualities.Unknown.value,
                                        referer = "$mainUrl/"
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
                if (src.isNotEmpty()) {
                    sources.add(
                        ExtractorLink(
                            name = name,
                            source = name,
                            url = src,
                            isM3u8 = src.contains(".m3u8"),
                            quality = Qualities.Unknown.value,
                            referer = "$mainUrl/"
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
