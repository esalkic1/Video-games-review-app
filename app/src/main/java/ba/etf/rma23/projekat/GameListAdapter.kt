package ba.etf.rma23.projekat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.spirala.R

class GameListAdapter (
    private var games: List<Game>, private val onItemClicked: (game: Game) -> Unit
) : RecyclerView.Adapter<GameListAdapter.GameViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_game, parent, false)
        return GameViewHolder(view)
    }
    override fun getItemCount(): Int = games.size
    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.gameTitle.text = games[position].title;
        val genreMatch: String = games[position].genre
        holder.gameRating.text = games[position].rating.toString()
        holder.gamePlatform.text = games[position].platform
        holder.gameReleaseDate.text = games[position].releaseDate
        //Pronalazimo id drawable elementa na osnovu naziva zanra
        /*val context: Context = holder.movieImage.context
        var id: Int = context.resources
            .getIdentifier(genreMatch, "drawable", context.packageName)
        if (id==0) id=context.resources
            .getIdentifier("picture1", "drawable", context.packageName)
        holder.movieImage.setImageResource(id)*/
        holder.itemView.setOnClickListener{ onItemClicked(games[position]) }
    }
    fun updateGames(games: List<Game>) {
        this.games = games
        notifyDataSetChanged()
    }
    inner class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gameRating: TextView = itemView.findViewById(R.id.game_rating_textview)
        val gameTitle: TextView = itemView.findViewById(R.id.item_title_textview)
        val gameReleaseDate: TextView = itemView.findViewById(R.id.game_release_date_textview)
        val gamePlatform: TextView = itemView.findViewById(R.id.game_platform_textview)
    }
}