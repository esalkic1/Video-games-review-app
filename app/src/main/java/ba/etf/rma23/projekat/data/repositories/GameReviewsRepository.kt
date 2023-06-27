package ba.etf.rma23.projekat.data.repositories

import android.content.Context
import android.net.ConnectivityManager
import ba.etf.rma23.projekat.AppDatabase
import ba.etf.rma23.projekat.GameReview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.RequestBody.Companion.toRequestBody
import java.net.ConnectException
import java.net.UnknownHostException
import kotlin.concurrent.thread

object GameReviewsRepository {

    var accHash: String = "cc34599c-0a71-4dae-8a36-4601b74c0375"

   suspend fun getOfflineReviews(context: Context):List<GameReview>{
       return withContext(Dispatchers.IO) {
           var db = AppDatabase.getInstance(context)
           var movies = db!!.gameReviewDao().getAll()
           return@withContext movies
       }
   }

    suspend fun writeReview(context: Context,gameReview: GameReview) : String?{
        return withContext(Dispatchers.IO) {
            try{
                var db = AppDatabase.getInstance(context)
                val nextId = db!!.gameReviewDao().getNextId() ?: 0 // Get the next available ID or start from 1 if null
                gameReview.id = nextId.toLong()
                db!!.gameReviewDao().insertAll(gameReview)
                return@withContext "success"
            }
            catch(error:Exception){
                return@withContext null
            }
        }
    }

    suspend fun sendOfflineReviews(context: Context):Int{
        return withContext(Dispatchers.IO){
            var successfullySent = 0
            val dbReviews = getOfflineReviews(context).distinctBy { it.id }
            for (i in dbReviews.indices){
                if (dbReviews[i].online == false){
                    val response = sendReview(context, dbReviews[i])    // response: Boolean
                    if (response){
                        successfullySent += 1
                        dbReviews[i].online = true
                    }
                }
            }
            return@withContext successfullySent
        }
    }

    suspend fun sendReview(context: Context, gameReview: GameReview): Boolean {
        return withContext(Dispatchers.IO) {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo

            if (networkInfo == null || !networkInfo.isConnected) {
                // No internet connection, save the review to the database
                gameReview.online = false
                writeReview(context, gameReview) // write the review to the database
                return@withContext false
            }

            try {
                val text = "{\n" +
                        "  \"review\": \"" + gameReview.review + "\",\n" +
                        "  \"rating\": " + gameReview.rating + "\n" +
                        "}"

                val savedGames = AccountGamesRepository.getSavedGames()
                var alreadySaved = false
                for (i in savedGames.indices) {
                    if (savedGames[i].id == gameReview.igdb_id) {
                        alreadySaved = true
                    }
                }
                if (!alreadySaved) {
                    val game = GamesRepository.getGamesByID(gameReview.igdb_id)
                    if (game.size != 0) {
                        AccountGamesRepository.saveGame(game[0])
                    }
                }

                val response = AccountApiConfig.retrofit.sendGameReview(
                    accHash,
                    gameReview.igdb_id,
                    text.toRequestBody()
                )

                if (response.code() == 200) {
                    val db = AppDatabase.getInstance(context)
                    db.gameReviewDao().setOnlineById(gameReview.id)
                    return@withContext true
                } else {
                    gameReview.online = false
                    writeReview(context, gameReview) // write the review to the database
                }

                return@withContext false
            } catch (e: UnknownHostException) {
                gameReview.online = false
                writeReview(context, gameReview) // write the review to the database
                return@withContext false
            } catch (e: ConnectException) {
                gameReview.online = false
                writeReview(context, gameReview) // write the review to the database
                return@withContext false
            } catch (e: NullPointerException) {
                gameReview.online = false
                writeReview(context, gameReview) // write the review to the database
                return@withContext false
            }
        }
    }





    suspend fun getReviewsForGame(igdb_id:Int):List<GameReview>{
        return withContext(Dispatchers.IO){
            val response = AccountApiConfig.retrofit.getReviewsForGame(igdb_id)
            val gameReviews = response.body()
            return@withContext gameReviews!!
        }
    }


}