package com.shahidmbc.ShahidMBC

import com.lagradost.cloudstream3.plugins.CloudstreamPlugin
import com.lagradost.cloudstream3.plugins.BasePlugin
import android.content.Context

@CloudstreamPlugin
class ShahidMBCPlugin : BasePlugin() {
    override fun load() {
        registerMainAPI(ShahidMBC())
    }
}
