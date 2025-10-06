package com.cimaclub.CimaClub

import com.lagradost.cloudstream3.plugins.CloudstreamPlugin
import com.lagradost.cloudstream3.plugins.BasePlugin
import android.content.Context

@CloudstreamPlugin
class CimaClubPlugin : BasePlugin() {
    override fun load() {
        registerMainAPI(CimaClub())
    }
}
