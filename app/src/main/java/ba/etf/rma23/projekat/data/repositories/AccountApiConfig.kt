package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AccountApiConfig {
    val accountGameDeserializer =
        GsonBuilder().registerTypeAdapter(Game::class.java, AccountGameDeserialize()).create()
    val retrofit: AccountApi = Retrofit.Builder()
        .baseUrl("https://rma23ws.onrender.com/")
        .addConverterFactory(GsonConverterFactory.create(accountGameDeserializer))
        .build()
        .create(AccountApi::class.java)
}