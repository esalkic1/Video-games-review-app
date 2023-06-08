package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import com.example.spirala.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.RequestBody.Companion.toRequestBody


object AccountGamesRepository {
    var accHash: String = BuildConfig.ACC_HASH
    var userAge: Int = 0
    //var favoriteGames: List<Game> = emptyList()

    suspend fun setHash(acHash:String):Boolean{     // treba popraviti
        return withContext(Dispatchers.IO) {
            accHash = acHash;
            if(accHash != acHash) return@withContext false
            return@withContext true
        }
    }

    suspend fun getHash():String{
        return withContext(Dispatchers.IO){
            return@withContext accHash
        }
    }

    suspend fun saveGame(game: Game):Game{
        return withContext(Dispatchers.IO){
            val text = "{\n" +
                    "  \"game\": {\n" +
                    "    \"igdb_id\": "+ game.id+ ",\n" +
                    "    \"name\": \""+ game.title +"\"\n" +
                    "  }\n" +
                    "}"
            val savedGames = getSavedGames()
            if (game in savedGames) return@withContext game           // ne znam treba li da moze dodati igru koja je vec na api-u
            var response = AccountApiConfig.retrofit.saveGame(accHash, text.toRequestBody())
            val responseBody = response.body()
            return@withContext responseBody!!
        }
    }

    suspend fun getSavedGames():List<Game>{
        return withContext(Dispatchers.IO){
            var response = AccountApiConfig.retrofit.getSavedGames(accHash)
            val responseBody: ArrayList<Game> = response.body() as ArrayList<Game>
            for (i in responseBody?.indices!!){   // valjda treba da vrati cijeli game kao sa IGDB
                responseBody[i] = GamesRepository.getGamesByID(responseBody.get(i).id).get(0)
            }
            GamesRepository.shownGames = responseBody
            return@withContext responseBody!!
        }
    }

    suspend fun removeGame(id:Int):Boolean{   // ne vraca dobro
        return withContext(Dispatchers.IO){
            var allGames = AccountApiConfig.retrofit.getSavedGames(accHash)
            for (i in allGames.body()?.indices!!){
                if (allGames.body()!!.get(i).id == id){
                    AccountApiConfig.retrofit.removeGame(accHash, id)
                    return@withContext true
                }
            }
            return@withContext false
        }
    }

    suspend fun removeNonSafe():Boolean{
        return withContext(Dispatchers.IO){
            var response = AccountApiConfig.retrofit.getSavedGames(accHash)
            val responseBody = response.body()
            for (i in responseBody?.indices!!){
                if(responseBody.get(i).esrbRating == "") continue
                if(responseBody.get(i).esrbRating.toInt() == 2){
                    if(userAge < 7) removeGame(responseBody.get(i).id)
                }
                if(responseBody.get(i).esrbRating.toInt() == 3){
                    if(userAge < 12) removeGame(responseBody.get(i).id)
                }
                if(responseBody.get(i).esrbRating.toInt() == 4){
                    if(userAge < 16) removeGame(responseBody.get(i).id)
                }
                if(responseBody.get(i).esrbRating.toInt() == 5 || responseBody.get(i).esrbRating.toInt() == 12){
                    if(userAge < 18) removeGame(responseBody.get(i).id)
                }
                if(responseBody.get(i).esrbRating.toInt() == 9){
                    if(userAge < 10) removeGame(responseBody.get(i).id)
                }
                if(responseBody.get(i).esrbRating.toInt() == 10){
                    if(userAge < 13) removeGame(responseBody.get(i).id)
                }
                if(responseBody.get(i).esrbRating.toInt() == 11){
                    if(userAge < 17) removeGame(responseBody.get(i).id)
                }
            }
            return@withContext true
        }
    }

    suspend fun getGamesContainingString(query:String):List<Game>{
        return withContext(Dispatchers.IO){
            var response = AccountApiConfig.retrofit.getGamesContainingString(accHash)
            var responseBody = response.body()
            responseBody = responseBody?.filter { it.title.lowercase().contains(query.lowercase()) }
            return@withContext responseBody!!
        }
    }

    suspend fun setAge(age:Int):Boolean{
        return withContext(Dispatchers.IO){
            if (age < 3 || age > 100) return@withContext false
            userAge = age
            if (userAge != age) return@withContext false
            return@withContext true
        }
    }


}