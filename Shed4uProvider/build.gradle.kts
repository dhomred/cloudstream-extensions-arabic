// use an integer for version numbers
version = 1

cloudstream {
    language = "ar"
    
    sources {
        source {
            name = "Shed4u"
            api = "com.shed4u.Shed4uProvider"
        }
    }
    
    status = 1
    tvTypes = listOf("TvSeries", "Movie", "Anime")
    iconUrl = "https://www.google.com/s2/favicons?domain=shed4u.cam&sz=%size%"
    authors = "AntiBotDev"
}