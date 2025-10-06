package com.topcinema.TopCinema

import com.lagradost.cloudstream3.plugins.CloudstreamPlugin
import com.lagradost.cloudstream3.plugins.BasePlugin
import android.content.Context

@CloudstreamPlugin
class TopCinemaPlugin : BasePlugin() {
    override fun load() {
        registerMainAPI(TopCinema())
    }
}
