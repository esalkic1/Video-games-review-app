package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import com.example.spirala.BuildConfig
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

private const val clientID : String = BuildConfig.CLIENT_ID
private const val authorization: String = BuildConfig.AUTHORIZATION

interface GameApi {
    @Headers(
        "Client-ID: $clientID",
        "Authorization: $authorization",
        "Content-Type: application/json"
    )
    @POST("games")
    suspend fun getAllGames(
    @Body text: RequestBody
    ): Response<List<Game>>


    @Headers(
        "Client-ID: $clientID",
        "Authorization: $authorization",
        "Content-Type: application/json"
    )
    @POST("games")
    suspend fun getGamesByName(
    @Body text: RequestBody
    ): Response<List<Game>>


    @Headers(
        "Client-ID: $clientID",
        "Authorization: $authorization",
        "Content-Type: application/json"
    )
    @POST("games")
    suspend fun getGamesByID(
        @Body text: RequestBody
    ): Response<List<Game>>




}