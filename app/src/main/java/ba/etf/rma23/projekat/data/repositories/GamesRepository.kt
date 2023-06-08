package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import okhttp3.RequestBody.Companion.toRequestBody

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

object GamesRepository {

    var shownGames = emptyList<Game>()

    suspend fun getAllGames(
    ) : List<Game>{
        return withContext(Dispatchers.IO) {
            var a: String = "fields id, name, platforms.name, release_dates.human, age_ratings.rating, cover.url, involved_companies.company.name, genres.name, summary;"
            var response = IGDBApiConfig.retrofit.getAllGames(a.toRequestBody())
            val responseBody = response.body()
            shownGames = responseBody!!
            return@withContext responseBody!!
        }
    }

    suspend fun getGamesByName(name:String):List<Game>{
        return withContext(Dispatchers.IO){
            var text: String = "fields id, name, platforms.name, release_dates.human, age_ratings.rating, " +
                    "cover.url, involved_companies.company.name, genres.name, summary; where name ~ \"%"+ name +"%\"; limit 100;"
            var response = IGDBApiConfig.retrofit.getGamesByName(text.toRequestBody())
            val responseBody = response.body()
            shownGames = responseBody!!
            return@withContext responseBody!!
        }
    }

    suspend fun getGamesByID(id:Int):List<Game>{
        return withContext(Dispatchers.IO){
            var text: String = "fields id, name, platforms.name, release_dates.human, age_ratings.rating, " +
                    "cover.url, involved_companies.company.name, genres.name, summary; where id = "+ id + ";"
            var response = IGDBApiConfig.retrofit.getGamesByID(text.toRequestBody())
            val responseBody = response.body()
            return@withContext responseBody!!
        }
    }


    suspend fun getGamesSafe(name:String):List<Game>{  // ne znam sta treba da radi kad je esrb rating = "", stavio sam da dodaje u listu
        return withContext(Dispatchers.IO){
            if (AccountGamesRepository.userAge == 0) return@withContext emptyList()
            var response = getGamesByName(name)
            val filteredResponse = mutableListOf<Game>()
            for (game in response) {
                if (game.esrbRating == ""){
                    filteredResponse.add(game)
                    continue
                }
                val esrbRating = game.esrbRating.toInt()
                val userAge = AccountGamesRepository.userAge

                when (esrbRating) {
                    2 -> if (userAge < 7) continue
                    3 -> if (userAge < 12) continue
                    4 -> if (userAge < 16) continue
                    5, 12 -> if (userAge < 18) continue
                    9 -> if (userAge < 10) continue
                    10 -> if (userAge < 13) continue
                    11 -> if (userAge < 17) continue
                }

                filteredResponse.add(game)
            }
            shownGames = filteredResponse
            return@withContext filteredResponse
        }
    }


    suspend fun sortGames():List<Game>{
        return withContext(Dispatchers.IO) {
            val games: List<Game> = shownGames
            val favoriteGames = AccountGamesRepository.getSavedGames()

            val sortedGames = games.sortedWith(
                compareByDescending<Game> { it in favoriteGames }
                    .thenBy { it.title }
            )

            return@withContext sortedGames
        }
    }

}