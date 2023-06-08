package ba.etf.rma23.projekat

import com.google.gson.annotations.SerializedName

data class Game(
    @SerializedName("id") val id: Int,  // ili long
    @SerializedName("name") val title: String,
    @SerializedName("platforms") val platform: String,
    @SerializedName("release_dates") val releaseDate: String,
    @SerializedName("parent_game") val rating: Double,                //??
    @SerializedName("cover") val coverImage: String,
    @SerializedName("age_ratings") val esrbRating: String,
    @SerializedName("involved_companies") val developer: String,
    @SerializedName("updated_at") val publisher: String,
    @SerializedName("genres") val genre: String,
    @SerializedName("summary") val description: String,
    @SerializedName("user_impressions") val userImpressions: List<UserImpression>,
)