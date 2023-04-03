package com.example.spirala

class GameData {
    companion object Factory{
        fun getAll(): List<Game>{
            return listOf(Game("FIFA 23", "PlayStation 5", "27.09.2022", 8.7, "https://image.api.playstation.com/vulcan/ap/rnd/202301/0312/GU1vXFJpbzGYNV6UN3U0Cnnb.png",
            "E", "EA Sports", "Electronic Arts", "sports", "Live out your football dreams as a manager or a player in Career Mode FIFA 23 brings you the ability to define your personality as a player, manage as some of football’s most famous names, and enjoy a" +
                        " new way to play your season with Playable Highlights in the most authentic FIFA Career Mode experience to date.", listOf(UserRating("ES21", 1680524096, 9.2),
                    UserReview("ES21", 1680524096, "Great game"))
            ), Game("NBA 2K23", "PlayStation 5", "08.09.2022", 8.2, "https://pbs.twimg.com/media/FXENTNzUEAAmdEX?format=jpg&name=900x900",
                "E", "Visual Concepts", "2K sports", "sports", "NBA 2K is the ultimate experience for basketball stars in the making, sending you on an immersive journey and bringing your NBA dreams to life.",
                listOf(UserRating("ES21", 1680524096, 9.2),
                    UserReview("ES21", 1680524096, "Great game"))
            ),
                Game("Minecraft", "PC", "18.11.2011", 9.1, "https://i.stack.imgur.com/dqVlX.png",
                    "E", "Mojang Studios", "Mojang Studios", "sandbox", "3D sandbox game that has no required goals to accomplish, allowing players a large amount of freedom in choosing how to play the game.",
                    listOf(UserRating("ES21", 1680524096, 9.2),
                        UserReview("ES21", 1680524096, "Great game"))
                ),
                Game("Red Dead Redemption 2", "PlayStation 4", "26.10.2018", 9.7, "https://upload.wikimedia.org/wikipedia/en/4/44/Red_Dead_Redemption_II.jpg",
                    "M", "Rockstar Games", "Rockstar Games", "adventure", "western action-adventure game set in an open world environment and played from a third-person perspective",
                    listOf(UserRating("ES21", 1680524096, 9.2),
                        UserReview("ES21", 1680524096, "Great game"))
                ),
                Game("GTA V", "PlayStation 4", "17.09.2013", 8.9, "https://upload.wikimedia.org/wikipedia/en/a/a5/Grand_Theft_Auto_V.png",
                    "M", "Rockstar Games", "Rockstar Games", "adventure", "an action-adventure game played from either a third-person or first-person perspective. Players complete missions—linear scenarios with set objectives—to progress through the story. Outside of the missions, players may freely roam the open world.",
                    listOf(UserRating("ES21", 1680524096, 9.2),
                        UserReview("ES21", 1680524096, "Great game"))
                ),
                Game("Football Manager 2023", "PC", "07.11.2022", 9.6, "https://upload.wikimedia.org/wikipedia/en/6/6a/Football_Manager_2023_cover_image.jpg",
                    "E", "Sports Interactive", "Sega", "sports", "Football Manager 2023 (officially abbreviated as FM23) is a football management simulation video game developed by Sports Interactive and published by Sega.",
                    listOf(UserRating("ES21", 1680524096, 9.2),
                        UserReview("ES21", 1680524096, "Great game"))),
                Game("Fortnite", "PC", "21.07.2017", 7.4, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS7I9t2dpy5e8J5jcCGg0nUwemfRiS3phW7Pg&usqp=CAU",
                    "E", "Epic Games", "Epic Games", "battle royal", "A battle royale game is an online multiplayer video game genre that blends last-man-standing gameplay with the survival, exploration and scavenging elements of a survival game.",
                    listOf(UserRating("ES21", 1680524096, 9.2),
                        UserReview("ES21", 1680524096, "Great game"))),
                Game("The Last of us", "Playstation 5", "14.06.2013", 8.3, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNNO6NCZJbmaPvKTziJJo1HUYB8pE9hI14Aw&usqp=CAU",
                    "E", "Naughty dog", "Sony", "adventure", "action-adventure survival horror game franchise created by Naughty Dog and Sony Interactive Entertainment.",
                    listOf(UserRating("ES21", 1680524096, 9.2),
                        UserReview("ES21", 1680524096, "Great game")))
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
    }
}