// use an integer for version numbers
version = 1

cloudstream {
    language = "ar"
    
    sources {
        source {
            name = "ShahidMBC"
            api = "com.shahidmbc.ShahidMBCProvider"
        }
    }
    
    status = 1
    tvTypes = listOf("TvSeries", "Movie", "Anime")
    iconUrl = "https://www.google.com/s2/favicons?domain=shahid.mbc.net&sz=%size%"
    authors = "AntiBotDev"
}