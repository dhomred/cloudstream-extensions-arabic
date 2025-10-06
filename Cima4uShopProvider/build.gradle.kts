// use an integer for version numbers
version = 1

cloudstream {
    language = "ar"
    
    sources {
        source {
            name = "Cima4uShop"
            api = "com.cima4ushop.Cima4uShopProvider"
        }
    }
    
    status = 1
    tvTypes = listOf("TvSeries", "Movie", "Anime")
    iconUrl = "https://www.google.com/s2/favicons?domain=cima4u.shop&sz=%size%"
    authors = "AntiBotDev"
}