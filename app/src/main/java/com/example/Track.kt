package com.example

data class Track(
    val title: String,
    val artist: String,
    val genre: String,
    val durationSeconds: Int
)

object TrackDatabase {
    val songs: List<Track> = listOf(
        // Rock (8 songs)
        Track("Smells Like Teen Spirit", "Nirvana", "Rock", 301),
        Track("Back In Black", "AC/DC", "Rock", 255),
        Track("Bohemian Rhapsody", "Queen", "Rock", 355),
        Track("Another Brick In The Wall", "Pink Floyd", "Rock", 239),
        Track("Stairway To Heaven", "Led Zeppelin", "Rock", 482),
        Track("Creep", "Radiohead", "Rock", 236),
        Track("Do I Wanna Know?", "Arctic Monkeys", "Rock", 272),
        Track("Seven Nation Army", "The White Stripes", "Rock", 231),

        // Pop (9 songs)
        Track("Bad Guy", "Billie Eilish", "Pop", 194),
        Track("Blinding Lights", "The Weeknd", "Pop", 200),
        Track("Levitating", "Dua Lipa", "Pop", 203),
        Track("Shake It Off", "Taylor Swift", "Pop", 219),
        Track("As It Was", "Harry Styles", "Pop", 167),
        Track("Drivers License", "Olivia Rodrigo", "Pop", 242),
        Track("Shape of You", "Ed Sheeran", "Pop", 233),
        Track("Hello", "Adele", "Pop", 295),
        Track("Uptown Funk", "Bruno Mars & Mark Ronson", "Pop", 270),

        // Rap (8 songs)
        Track("Lose Yourself", "Eminem", "Rap", 326),
        Track("Hotline Bling", "Drake", "Rap", 267),
        Track("HUMBLE.", "Kendrick Lamar", "Rap", 177),
        Track("Rockstar", "Post Malone & 21 Savage", "Rap", 218),
        Track("Sicko Mode", "Travis Scott", "Rap", 312),
        Track("California Love", "Tupac Shakur", "Rap", 284),
        Track("Hey Ya!", "Outkast", "Rap", 235),
        Track("Gangsta's Paradise", "Coolio", "Rap", 240),

        // Phonk (7 songs)
        Track("Murder In My Mind", "KORDHELL", "Phonk", 145),
        Track("ODIUM", "LXST CXNTURY", "Phonk", 190),
        Track("Close Eyes", "DVRST", "Phonk", 132),
        Track("Sahara", "Hensonn", "Phonk", 171),
        Track("Metamorphosis", "Interworld", "Phonk", 143),
        Track("GigaChad Theme", "g3ox_em", "Phonk", 141),
        Track("North Memphis", "Pharmacist", "Phonk", 164),

        // Électro (8 songs)
        Track("Get Lucky", "Daft Punk", "Électro", 248),
        Track("Wake Me Up", "Avicii", "Électro", 247),
        Track("Animals", "Martin Garrix", "Électro", 304),
        Track("Titanium", "David Guetta & Sia", "Électro", 245),
        Track("Don't You Worry Child", "Swedish House Mafia", "Électro", 212),
        Track("Summer", "Calvin Harris", "Électro", 222),
        Track("Firestone", "Kygo", "Électro", 273),
        Track("Latch", "Disclosure & Sam Smith", "Électro", 256),

        // Jazz (8 songs)
        Track("So What", "Miles Davis", "Jazz", 562),
        Track("My Favorite Things", "John Coltrane", "Jazz", 821),
        Track("Take Five", "Dave Brubeck", "Jazz", 324),
        Track("What A Wonderful World", "Louis Armstrong", "Jazz", 137),
        Track("Feeling Good", "Nina Simone", "Jazz", 174),
        Track("Take the A Train", "Duke Ellington", "Jazz", 172),
        Track("Fly Me To The Moon", "Frank Sinatra", "Jazz", 147),
        Track("Dream A Little Dream Of Me", "Ella Fitzgerald & Louis Armstrong", "Jazz", 185),

        // Metal (6 songs)
        Track("Master of Puppets", "Metallica", "Metal", 515),
        Track("The Trooper", "Iron Maiden", "Metal", 251),
        Track("Paranoid", "Black Sabbath", "Metal", 168),
        Track("Chop Suey!", "System Of A Down", "Metal", 210),
        Track("Duality", "Slipknot", "Metal", 252),
        Track("Symphony of Destruction", "Megadeth", "Metal", 242),

        // Variété Française (8 songs)
        Track("Papaoutai", "Stromae", "Variété Française", 232),
        Track("La Vie En Rose", "Edith Piaf", "Variété Française", 185),
        Track("La Bohème", "Charles Aznavour", "Variété Française", 245),
        Track("L'aventurier", "Indochine", "Variété Française", 289),
        Track("Balance Ton Quoi", "Angèle", "Variété Française", 189),
        Track("Pour que tu m'aimes encore", "Céline Dion", "Variété Française", 254),
        Track("Je l'aime à mourir", "Francis Cabrel", "Variété Française", 162),
        Track("La Javanaise", "Serge Gainsbourg", "Variété Française", 149),

        // Reggae (5 songs)
        Track("One Love", "Bob Marley", "Reggae", 172),
        Track("No Woman, No Cry", "Bob Marley", "Reggae", 226),
        Track("Red Red Wine", "UB40", "Reggae", 183),
        Track("Sweat (A La La La La Long)", "Inner Circle", "Reggae", 226),
        Track("Many Rivers to Cross", "Jimmy Cliff", "Reggae", 164),

        // Classique (5 songs)
        Track("Symphony No. 9 (Ode to Joy)", "Beethoven", "Classique", 390),
        Track("Eine kleine Nachtmusik", "Mozart", "Classique", 350),
        Track("Spring (The Four Seasons)", "Vivaldi", "Classique", 190),
        Track("Air on the G String", "Bach", "Classique", 300),
        Track("Clair de Lune", "Debussy", "Classique", 305),

        // Blues (5 songs)
        Track("The Thrill Is Gone", "B.B. King", "Blues", 324),
        Track("House of the Blues", "Muddy Waters", "Blues", 156),
        Track("Pride and Joy", "Stevie Ray Vaughan", "Blues", 220),
        Track("Cross Road Blues", "Robert Johnson", "Blues", 150),
        Track("Stone Crazy", "Buddy Guy", "Blues", 430),

        // Lo-Fi (5 songs)
        Track("Blue In Green", "L.Dre", "Lo-Fi", 140),
        Track("Lofi Hip Hop Beats", "ChilledCow", "Lo-Fi", 180),
        Track("The Girl I Haven't Met", "Kudasaibeats", "Lo-Fi", 135),
        Track("Affection", "Jinsang", "Lo-Fi", 142),
        Track("Phantasia", "Idealism", "Lo-Fi", 130),

        // Country (5 songs)
        Track("Ring of Fire", "Johnny Cash", "Country", 157),
        Track("Jolene", "Dolly Parton", "Country", 160),
        Track("Take Me Home, Country Roads", "John Denver", "Country", 190),
        Track("On the Road Again", "Willie Nelson", "Country", 153),
        Track("Man! I Feel Like a Woman!", "Shania Twain", "Country", 233),

        // Disco (5 songs)
        Track("Stayin' Alive", "Bee Gees", "Disco", 285),
        Track("Dancing Queen", "ABBA", "Disco", 230),
        Track("I Will Survive", "Gloria Gaynor", "Disco", 201),
        Track("Hot Stuff", "Donna Summer", "Disco", 314),
        Track("Le Freak", "Chic", "Disco", 328),

        // K-Pop (5 songs)
        Track("Dynamite", "BTS", "K-Pop", 199),
        Track("DDU-DU DDU-DU", "BLACKPINK", "K-Pop", 209),
        Track("Gangnam Style", "PSY", "K-Pop", 219),
        Track("Hype Boy", "NewJeans", "K-Pop", 179),
        Track("Fancy", "TWICE", "K-Pop", 215),

        // R&B (5 songs)
        Track("Pink + White", "Frank Ocean", "R&B", 184),
        Track("Kill Bill", "SZA", "R&B", 153),
        Track("If I Ain't Got You", "Alicia Keys", "R&B", 228),
        Track("Yeah!", "Usher", "R&B", 250),
        Track("Halo", "Beyonce", "R&B", 261)
    )

    /**
     * Recommends genres based on keywords from title and artist inputs.
     */
    fun detectGenre(title: String, artist: String): String? {
        val searchStr = "${title.lowercase()} ${artist.lowercase()}"
        if (searchStr.isEmpty()) return null

        val rockKeywords = listOf("rock", "nirvana", "ac/dc", "queen", "floyd", "led", "zeppelin", "radiohead", "arctic", "monkeys", "stripes", "slash", "guitar")
        val popKeywords = listOf("pop", "billie", "eilish", "weeknd", "lipa", "swift", "styles", "olivia", "sheeran", "adele", "mars", "justin", "ariana")
        val rapKeywords = listOf("rap", "eminem", "drake", "kendrick", "lamar", "post", "malone", "travis", "scott", "tupac", "hip", "hop", "gangsta")
        val phonkKeywords = listOf("phonk", "kordhell", "lxst", "dvrst", "hensonn", "interworld", "drift", "giga", "chad", "memphis")
        val electroKeywords = listOf("electro", "daft", "punk", "avicii", "garrix", "guetta", "swedish", "house", "calvin", "harris", "kygo", "disco", "techno", "electronic")
        val jazzKeywords = listOf("jazz", "miles", "davis", "coltrane", "brubeck", "armstrong", "nina", "simone", "ellington", "sinatra", "fitzgerald", "soul")
        val metalKeywords = listOf("metal", "metallica", "maiden", "sabbath", "slipknot", "system", "down", "megadeth", "heavy", "thrash")
        val frenchKeywords = listOf("variété", "variete", "française", "francaise", "fran", "stromae", "piaf", "aznavour", "indochine", "angèle", "angele", "dion", "cabrel", "gainsbourg")
        val reggaeKeywords = listOf("reggae", "marley", "ub40", "inner", "circle", "cliff", "jamaica", "ska")
        val classiqueKeywords = listOf("classique", "classical", "beethoven", "mozart", "vivaldi", "bach", "debussy", "chopin", "symphony", "sonata")
        val bluesKeywords = listOf("blues", "king", "muddy", "waters", "vaughan", "johnson", "buddy", "guy")
        val lofiKeywords = listOf("lofi", "lo-fi", "dre", "chilledcow", "kudasaibeats", "jinsang", "idealism", "relax", "chillhop")
        val countryKeywords = listOf("country", "cash", "parton", "denver", "nelson", "twain", "nashville", "cowboy")
        val discoKeywords = listOf("disco", "gees", "abba", "gaynor", "summer", "chic", "funk")
        val kpopKeywords = listOf("kpop", "k-pop", "bts", "blackpink", "psy", "newjeans", "twice", "korea")
        val rnbKeywords = listOf("r&b", "rnb", "ocean", "sza", "keys", "usher", "beyonce", "rihanna", "chris", "brown")

        if (rockKeywords.any { searchStr.contains(it) }) return "Rock"
        if (popKeywords.any { searchStr.contains(it) }) return "Pop"
        if (rapKeywords.any { searchStr.contains(it) }) return "Rap"
        if (phonkKeywords.any { searchStr.contains(it) }) return "Phonk"
        if (electroKeywords.any { searchStr.contains(it) }) return "Électro"
        if (jazzKeywords.any { searchStr.contains(it) }) return "Jazz"
        if (metalKeywords.any { searchStr.contains(it) }) return "Metal"
        if (frenchKeywords.any { searchStr.contains(it) }) return "Variété Française"
        if (reggaeKeywords.any { searchStr.contains(it) }) return "Reggae"
        if (classiqueKeywords.any { searchStr.contains(it) }) return "Classique"
        if (bluesKeywords.any { searchStr.contains(it) }) return "Blues"
        if (lofiKeywords.any { searchStr.contains(it) }) return "Lo-Fi"
        if (countryKeywords.any { searchStr.contains(it) }) return "Country"
        if (discoKeywords.any { searchStr.contains(it) }) return "Disco"
        if (kpopKeywords.any { searchStr.contains(it) }) return "K-Pop"
        if (rnbKeywords.any { searchStr.contains(it) }) return "R&B"

        return null
    }

    /**
     * Returns a list of genres close to the given genre to complete playlists when needed.
     */
    fun getRelatedGenres(genre: String): List<String> {
        return when (genre) {
            "Rock" -> listOf("Metal", "Pop", "Country")
            "Metal" -> listOf("Rock")
            "Pop" -> listOf("Variété Française", "Électro", "Rock", "Disco", "K-Pop", "R&B")
            "Rap" -> listOf("Phonk", "Pop", "R&B")
            "Phonk" -> listOf("Rap", "Électro")
            "Électro" -> listOf("Phonk", "Pop", "Disco")
            "Jazz" -> listOf("Pop", "Variété Française", "Blues")
            "Variété Française" -> listOf("Pop", "Jazz")
            "Reggae" -> listOf("Blues", "Pop")
            "Classique" -> listOf("Jazz", "Lo-Fi")
            "Blues" -> listOf("Jazz", "Reggae")
            "Lo-Fi" -> listOf("Jazz", "Classique")
            "Country" -> listOf("Rock", "Pop")
            "Disco" -> listOf("Électro", "Pop")
            "K-Pop" -> listOf("Pop", "R&B")
            "R&B" -> listOf("Pop", "Rap")
            else -> listOf("Pop", "Rock")
        }
    }
}
