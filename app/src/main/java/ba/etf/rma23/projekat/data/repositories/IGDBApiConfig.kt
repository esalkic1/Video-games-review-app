package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object IGDBApiConfig {
    val gameDeserializer =
        GsonBuilder().registerTypeAdapter(Game::class.java, GameDeserialize()).create()
    val retrofit: GameApi = Retrofit.Builder()
        .baseUrl("https://api.igdb.com/v4/")
        .addConverterFactory(GsonConverterFactory.create(gameDeserializer))
        .build()
        .create(GameApi::class.java)
}