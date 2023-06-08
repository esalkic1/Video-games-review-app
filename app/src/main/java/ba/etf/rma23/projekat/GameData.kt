package ba.etf.rma23.projekat

class GameData {
    companion object Factory{
        fun getAll(): List<Game>{
            return listOf(
                Game(0,
                    "FIFA 23",
                    "PlayStation 5",
                    "27.09.2022.",
                    4.1,
                    "fifa23",
                    "Everyone",
                    "EA Sports",
                    "Electronic Arts",
                    "Sports",
                    "Live out your football dreams as a manager or a player in Career Mode FIFA 23 brings you the ability to define your personality as a player, manage as some of football’s most famous names, and enjoy a" +
                            " new way to play your season with Playable Highlights in the most authentic FIFA Career Mode experience to date.",
                    listOf(
                        UserRating("EmirS21", 250, 4.7),
                        UserReview("EmirS21", 400, "Great game"),
                        UserRating("Darwizzy", 50, 5.0),
                        UserReview("Clarismario", 500, "Amazing graphics, Career mode is improved"),
                        UserRating("Clarismario", 1000, 5.0)
                    )
                ),
                Game(1,
                    "NBA 2K23",
                    "PlayStation 5",
                    "08.09.2022.",
                    3.8,
                    "nba2k23",
                    "Everyone",
                    "Visual Concepts",
                    "2K sports",
                    "Sports",
                    "NBA 2K23 is the ultimate basketball simulation game, where you can live out your hoop dreams and lead your team to glory. With improved graphics and animation, more realistic player movements and physics, " +
                            "and an enhanced MyCareer mode that lets you create your own player and guide them through their NBA journey, you'll feel like you're part of the action.",
                    listOf(
                        UserRating("EmirS21", 300, 4.0),
                        UserReview("EmirS21", 50, "Great game"),
                        UserRating("Alphonso_Davies10", 600, 3.2),
                        UserReview(
                            "Alphonso_Davies10",
                            1000,
                            "Too many unfixed errors since last edition"
                        ),
                        UserReview("SlowRw5", 70, "I like it")
                    )
                ),
                Game(2,
                    "Minecraft",
                    "PC",
                    "18.11.2011.",
                    4.2,
                    "minecraft",
                    "Everyone 10+",
                    "Mojang Studios",
                    "Mojang Studios",
                    "Sandbox",
                    "Minecraft is a game that lets you unleash your creativity and imagination, as you explore a vast and endless world made entirely of blocks. Whether you're building towering castles, digging deep into the earth to find precious resources, or battling hordes of monsters in survival mode, Minecraft offers an endless " +
                            "array of possibilities for players of all ages.",
                    listOf(
                        UserRating("EmirS21", 500, 3.6),
                        UserReview("EmirS21", 600, "Fun to play with friends")
                    )
                ),
                Game(3,
                    "Red Dead Redemption 2",
                    "PlayStation 4",
                    "26.10.2018.",
                    4.7,
                    "rdr2",
                    "Mature",
                    "Rockstar Games",
                    "Rockstar Games",
                    "Adventure",
                    "Red Dead Redemption 2 is an epic open-world adventure game that transports you to the Wild West, where you can experience the gritty and harsh realities of life as an outlaw. With stunning graphics and a richly detailed world, you'll ride through vast landscapes, encounter dangerous wildlife and rival gangs, and " +
                            "engage in thrilling shootouts and heists.",
                    listOf(
                        UserRating("EmirS21", 2000, 4.9),
                        UserReview(
                            "EmirS21",
                            150,
                            "Unbelievably amazing graphics, immense experience"
                        )
                    )
                ),
                Game(4,
                    "GTA V",
                    "PlayStation 4",
                    "17.09.2013.",
                    4.5,
                    "gtav",
                    "Mature",
                    "Rockstar Games",
                    "Rockstar Games",
                    "Adventure",
                    "Grand Theft Auto V is a thrilling open-world game that puts you in the driver's seat of a life of crime, as you take on the role of three different protagonists in the seedy underworld of Los Santos. With a massive and detailed map that includes sprawling cities, deserts, and mountains, you can wreak havoc with an arsenal " +
                            "of weapons, high-speed vehicles, and advanced technology.",
                    listOf(
                        UserRating("EmirS21", 60, 4.1),
                        UserReview("EmirS21", 100, "A true classic, spent hours playing")
                    )
                ),
                Game(5,
                    "Football Manager 2023",
                    "PC",
                    "07.11.2022.",
                    5.0,
                    "fm23",
                    "Everyone",
                    "Sports Interactive",
                    "Sega",
                    "Sports",
                    "Football Manager 2023 is the ultimate football management simulation game, where you take charge of your favorite club and lead them to glory. With updated player and team data, improved match engine and tactics, and new features like Club Vision and Match Sharpness, you'll experience the thrill of building a winning team and managing " +
                            "every aspect of the club.",
                    listOf(
                        UserRating("EmirS21", 700, 5.0),
                        UserReview("EmirS21", 200, "Too addictive, but you can't not love it")
                    )
                ),
                Game(6,
                    "Fortnite",
                    "PC",
                    "21.07.2017.",
                    2.9,
                    "fortnite",
                    "Teen",
                    "Epic Games",
                    "Epic Games",
                    "Battle royal",
                    "Fortnite is an action-packed online multiplayer game that has taken the world by storm, offering a unique blend of intense combat, building mechanics, and social interaction. In the game's Battle Royale mode, 100 players are dropped onto an island and must fight to be the last one standing, using a variety of weapons and tactics to " +
                            "outwit their opponents.",
                    listOf(
                        UserRating("EmirS21", 1200, 3.1),
                        UserReview("EmirS21", 600, "Fun for a bit, gets boring")
                    )
                ),
                Game(7,
                    "The Last of Us",
                    "Playstation 5",
                    "14.06.2013.",
                    3.4,
                    "tlou",
                    "Mature",
                    "Naughty dog",
                    "Sony",
                    "Adventure",
                    "The Last of Us is a gripping post-apocalyptic adventure game that takes you on an emotional journey through a world ravaged by a deadly pandemic. In the game's story mode, you play as Joel, a hardened survivor, who is tasked with escorting a young girl named Ellie across a dangerous and brutal landscape to find a group of resistance fighters.",
                    listOf(
                        UserRating("EmirS21", 3800, 3.5),
                        UserReview("EmirS21", 5000, "Great story, bugs ruin it a bit")
                    )
                ),
                Game(8,
                    "CS: GO",
                    "PC",
                    "21.08.2012.",
                    3.1,
                    "csgo",
                    "Mature",
                    "Valve Corporations",
                    "Valve Corporations",
                    "First-Person shooter",
                    "Counter-Strike: Global Offensive, or CS:GO, is a legendary competitive first-person shooter that has become a staple of esports around the world. With its fast-paced gameplay, realistic weapons and tactics, and constant updates that introduce new content and gameplay features, CS:GO offers endless hours of excitement and challenge for players of all skill levels.",
                    listOf(
                        UserRating("EmirS21", 60, 2.9),
                        UserReview("EmirS21", 450, "Too many cheaters")
                    )
                ),
                Game(9,
                    "Rocket League",
                    "Playstation 4",
                    "07.07.2015",
                    2.7,
                    "rocketl",
                    "Everyone",
                    "Psyonix",
                    "Psyonix",
                    "Sports",
                    "Rocket League is a high-octane sports game that combines the thrill of soccer with the excitement of high-speed racing. In the game, you control a rocket-powered car as you attempt to score goals in a variety of arenas, using your vehicle's boost, jump, and flip abilities to outmaneuver and outscore your opponents.",
                    listOf(
                        UserRating("EmirS21", 850, 2.5),
                        UserReview("EmirS21", 200, "Pay to win game, didn't really enjoy")
                    )
                )
            )
        }

        fun getDetails(title:String): Game? {
            var games: List<Game>
            games = getAll()
            for (game in games){
                if (game.title.equals(title)) return game
            }
            return null
        }

        fun getSortedImpressions(title: String): List<UserImpression>{
            var impressions: ArrayList<UserImpression>
            impressions = arrayListOf()
            var game = getDetails(title)
            if (game != null) {
                for(impression in game.userImpressions){
                    impressions.add(impression)
                }
            }
            var sortedList = impressions.sortedWith(compareByDescending({it.timestamp}))
            return sortedList
        }
    }
}