package com.egydead.EgyDead

import com.lagradost.cloudstream3.plugins.CloudstreamPlugin
import com.lagradost.cloudstream3.plugins.BasePlugin
import android.content.Context

@CloudstreamPlugin
class EgyDeadPlugin : BasePlugin() {
    override fun load() {
        registerMainAPI(EgyDead())
    }
}
