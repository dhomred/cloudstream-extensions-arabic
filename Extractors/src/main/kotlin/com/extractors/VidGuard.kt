package com.extractors

import com.lagradost.cloudstream3.utils.ExtractorApi
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.utils.Qualities
import com.lagradost.cloudstream3.app
import java.net.URLDecoder
import android.util.Base64

open class VidGuard : ExtractorApi() {
    override val name = "VidGuard"
    override val mainUrl = "https://vidguard.to"
    override val requiresReferer = false

    // خوارزمية فك تشفير بسيطة
    private fun decryptUrl(encryptedUrl: String): String {
        return try {
            // محاولة فك التشفير باستخدام Base64
            if (encryptedUrl.contains("base64,")) {
                val base64Data = encryptedUrl.substringAfter("base64,")
                String(Base64.decode(base64Data, Base64.DEFAULT))
            } else {
                // فك تشفير URL
                URLDecoder.decode(encryptedUrl, "UTF-8")
            }
        } catch (e: Exception) {
            encryptedUrl
        }
    }

    override suspend fun getUrl(url: String, referer: String?): List<ExtractorLink> {
        val sources = mutableListOf<ExtractorLink>()
        
        try {
            val doc = app.get(url).document
            
            // البحث عن روابط مشفرة
            val scriptElements = doc.select("script")
            
            for (script in scriptElements) {
                val scriptData = script.data()
                
                // البحث عن البنية الخاصة بـ VidGuard
                if (scriptData.contains("vidguard") || scriptData.contains("encrypt") || scriptData.contains("decrypt")) {
                    try {
                        // استخراج الروابط المشفرة
                        val encryptedPatterns = listOf(
                            Regex("""['"]([^'"]*vidguard[^'"]*(?:=|%3D)[^'"]*)['"]"""),
                            Regex("""['"]([^'"]*base64[^'"]*[^'"]*)['"]"""),
                            Regex("""['"]([^'"]*encrypt[^'"]*[^'"]*)['"]"""),
                            Regex("""url['"]\s*=\s*['"]([^'"]+)['"]""")
                        )
                        
                        for (pattern in encryptedPatterns) {
                            val matches = pattern.findAll(scriptData)
                            
                            for (match in matches) {
                                var encryptedUrl = match.groupValues[1]
                                
                                // فك التشفير
                                val decryptedUrl = decryptUrl(encryptedUrl)
                                
                                if (decryptedUrl.isNotEmpty() && decryptedUrl.contains("http")) {
                                    sources.add(
                                        ExtractorLink(
                                            name = name,
                                            source = name,
                                            url = decryptedUrl,
                                            isM3u8 = decryptedUrl.contains(".m3u8"),
                                            quality = Qualities.Unknown.value,
                                            referer = mainUrl
                                        )
                                    )
                                }
                            }
                        }
                        
                        // البحث عن روابط مباشرة
                        val directPatterns = listOf(
                            Regex("""['"](https?://[^'"]*\.m3u8[^'"]*)['"]"""),
                            Regex("""['"](https?://[^'"]*\.mp4[^'"]*)['"]""")
                        )
                        
                        for (pattern in directPatterns) {
                            val matches = pattern.findAll(scriptData)
                            
                            for (match in matches) {
                                val directUrl = match.groupValues[1]
                                
                                sources.add(
                                    ExtractorLink(
                                        name = name,
                                        source = name,
                                        url = directUrl,
                                        isM3u8 = directUrl.contains(".m3u8"),
                                        quality = Qualities.Unknown.value,
                                        referer = mainUrl
                                    )
                                )
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
                if (src.isNotEmpty() && (src.contains("vidguard") || src.contains(".m3u8") || src.contains(".mp4"))) {
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
            
            // البحث عن عناصر خاصة بروابط التنزيل
            val specialElements = doc.select("[data-url], [data-link], [data-video]")
            for (element in specialElements) {
                val dataUrl = element.attr("data-url")
                val dataLink = element.attr("data-link")
                val dataVideo = element.attr("data-video")
                val link = dataUrl.ifEmpty { dataLink }.ifEmpty { dataVideo }
                
                if (link.isNotEmpty() && (link.contains("vidguard") || link.contains(".m3u8") || link.contains(".mp4"))) {
                    // فك التشفير إذا لزم الأمر
                    val finalLink = if (link.contains("=") || link.contains("%3D")) {
                        decryptUrl(link)
                    } else {
                        link
                    }
                    
                    if (finalLink.contains("http")) {
                        sources.add(
                            ExtractorLink(
                                name = name,
                                source = name,
                                url = finalLink,
                                isM3u8 = finalLink.contains(".m3u8"),
                                quality = Qualities.Unknown.value,
                                referer = mainUrl
                            )
                        )
                    }
                }
            }
            
        } catch (e: Exception) {
            // يمكن إضافة logging هنا
        }
        
        return sources
    }
}

// أنواع مختلفة من VidGuard
class VidGuardTo : VidGuard() {
    override val name = "VidGuard"
    override val mainUrl = "https://vidguard.to"
}

class VidGuardVp : VidGuard() {
    override val name = "VidGuard"
    override val mainUrl = "https://vidguard.vp"
}

class VidGuardIo : VidGuard() {
    override val name = "VidGuard"
    override val mainUrl = "https://vidguard.io"
}