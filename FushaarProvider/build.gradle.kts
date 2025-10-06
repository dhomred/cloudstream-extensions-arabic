// use an integer for version numbers
version = 1

cloudstream {
    language = "ar"
    
    sources {
        source {
            name = "Fushaar"
            api = "com.fushaar.FushaarProvider"
        }
    }
    
    status = 1
    tvTypes = listOf("TvSeries", "Movie", "Anime")
    iconUrl = "https://www.google.com/s2/favicons?domain=fushaar.com&sz=%size%"
    authors = "AntiBotDev"
}
