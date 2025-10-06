// use an integer for version numbers
version = 1

// Dependencies are needed for cloudstream to be able to fetch this extension
cloudstream {
    // language can be found here: https://github.com/recloudstream/cloudstream/blob/master/app/src/main/java/com/lagradost/cloudstream3/ui/settings/SettingsGeneral.kt#L386-L439
    language = "ar"
    
    // All the sources that this extension can use
    sources {
        source {
            name = "CimaClub"
            api = "com.cimaclub.CimaClubProvider"
        }
    }
    
    // The status of this extension, 1 = stable, 0 = unstable
    status = 1
    
    // List of types this extension can use, can be found here: https://recloudstream.github.io/docs/adding_types/
    tvTypes = listOf("TvSeries", "Movie", "Anime")
    
    // The icon url of this extension, optional
    iconUrl = "https://www.google.com/s2/favicons?domain=cimaclub.us&sz=%size%"
    
    // The authors of this extension
    authors = "AntiBotDev"
}