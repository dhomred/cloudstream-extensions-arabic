// use an integer for version numbers
version = 1

cloudstream {
    language = "ar"
    
    sources {
        source {
            name = "MovizLands"
            api = "com.movizlands.MovizLandsProvider"
        }
    }
    
    status = 1
    tvTypes = listOf("TvSeries", "Movie", "Anime")
    iconUrl = "https://www.google.com/s2/favicons?domain=movizlands.com&sz=%size%"
    authors = "AntiBotDev"
}