package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kotlinx.coroutines.*
import java.lang.reflect.Type

class AccountGameDeserialize: JsonDeserializer<Game> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Game {
        val jsonObject = json?.asJsonObject ?: JsonObject()
        val id = jsonObject.get("igdb_id")?.asInt ?: 0
        val name = jsonObject.get("name")?.asString ?: ""

        var game = Game(id, name, "", "", 0.0, "", "", "",
        "", "", "", emptyList()
        )

        runBlocking {
            val foundGame = CoroutineScope(Dispatchers.IO).async {
                GamesRepository.getGamesByID(id)
            }
            val found = foundGame.await()
            if (found.isNotEmpty()) game = found.get(0)
        }
        return game
    }
}