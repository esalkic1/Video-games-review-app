package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

class GameDeserialize: JsonDeserializer<Game> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Game {
        val jsonObject = json?.asJsonObject ?: JsonObject()
        return Game(
            id = jsonObject.get("id")?.asInt ?: 0,
            title = jsonObject.get("name")?.asString ?: "No title",
            platform = getPlatform(jsonObject),
            releaseDate = getReleaseDate(jsonObject),
            rating = getRating(jsonObject),
            coverImage = getCoverImage(jsonObject),
            esrbRating = getEsrbRating(jsonObject),
            developer = getCompany(jsonObject),
            publisher = getCompany(jsonObject),
            genre = getGenre(jsonObject),
            description = jsonObject.get("summary")?.asString ?: "",
            emptyList()  // userImpressions
        )
    }

    private fun getPlatform(jsonObject: JsonObject): String {
        val platforms = jsonObject.getAsJsonArray("platforms")
        return platforms?.get(0)?.asJsonObject?.get("name")?.asString ?: ""
    }

    private fun getGenre(jsonObject: JsonObject): String {
        val genres = jsonObject.getAsJsonArray("genres")
        return genres?.get(0)?.asJsonObject?.get("name")?.asString ?: ""
    }

    private fun getCompany(jsonObject: JsonObject): String {
        val involvedCompanies = jsonObject.getAsJsonArray("involved_companies")
        val company =  involvedCompanies?.get(0)?.asJsonObject?.getAsJsonObject("company")
        return company?.get("name")?.asString ?: ""
    }

    private fun getEsrbRating(jsonObject: JsonObject): String {
        val esrbArray = jsonObject.getAsJsonArray("age_ratings")
        val esrb = esrbArray?.get(0)?.asJsonObject
        return esrb?.get("rating")?.asString ?: ""
    }

    private fun getCoverImage(jsonObject: JsonObject): String {
        return jsonObject.getAsJsonObject("cover")?.get("url")?.asString ?: ""
    }

    private fun getRating(jsonObject: JsonObject): Double {
        val ratings = jsonObject.getAsJsonArray("age_ratings")
        val rating = ratings?.get(0)?.asJsonObject
        return rating?.get("rating")?.asDouble ?: 0.0
    }

    private fun getReleaseDate(jsonObject: JsonObject): String {
        val dates = jsonObject.getAsJsonArray("release_dates")
        val date = dates?.get(0)?.asJsonObject
        return date?.get("human")?.asString ?: ""
    }

}