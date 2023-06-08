package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import com.example.spirala.BuildConfig
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

private const val accHash: String = BuildConfig.ACC_HASH

interface AccountApi {

    @Headers(
        "Content-Type: application/json"
    )
    @GET("account/{aid}/games")
    suspend fun getSavedGames(
        @Path("aid") accHash: String,
    ): Response<List<Game>>

    @Headers(
        "Content-Type: application/json"
    )
    @POST("account/{aid}/game")
    suspend fun saveGame(
        @Path("aid") accHash: String,
        @Body() text: RequestBody
    ): Response<Game>

    @Headers(
        "Content-Type: application/json"
    )
    @DELETE("account/{aid}/game/{gid}/")
    suspend fun removeGame(
        @Path("aid") accHash: String,
        @Path("gid") gameID: Int
    )

    @Headers(
        "Content-Type: application/json"
    )
    @GET("account/{aid}/games")
    suspend fun getGamesContainingString(
        @Path("aid") accHash: String,
    ): Response<List<Game>>
}