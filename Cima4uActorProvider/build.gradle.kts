// use an integer for version numbers
version = 1

cloudstream {
    language = "ar"
    
    sources {
        source {
            name = "Cima4uActor"
            api = "com.cima4uactor.Cima4uActorProvider"
        }
    }
    
    status = 1
    tvTypes = listOf("TvSeries", "Movie", "Anime")
    iconUrl = "https://www.google.com/s2/favicons?domain=cima4u.actor&sz=%size%"
    authors = "AntiBotDev"
}