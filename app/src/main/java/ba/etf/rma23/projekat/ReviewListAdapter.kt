package ba.etf.rma23.projekat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.spirala.R

class ReviewListAdapter (
    private var items: List<Any>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val RATING = 0
    private val REVIEW = 1

    override fun getItemViewType(position: Int): Int {
        if (items[position] is UserRating) {
            return RATING
        } else if (items[position] is UserReview) {
            return REVIEW
        }
        return -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            RATING -> {
                val v1: View = inflater.inflate(R.layout.item_rating, parent, false)
                viewHolder = RatingViewHolder(v1)
            }
            REVIEW -> {
                val v2: View = inflater.inflate(R.layout.item_review, parent, false)
                viewHolder = ReviewViewHolder(v2)
            }
            else -> {
                val v: View = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
                viewHolder = ReviewViewHolder(v)   // ne znam da li moze ovako??? ili mozda RecyclerViewSimpleTextViewHolder
            }
        }
        return viewHolder
        /*val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_game, parent, false)
        return ReviewViewHolder(view)*/
    }

    override fun getItemCount(): Int = items.size

    /*override fun onBindViewHolder(holder: GameListAdapter.GameViewHolder, position: Int) {
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
    }*/

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder.itemViewType) {
            RATING -> {
                val vh1 = viewHolder as RatingViewHolder
                configureRatingViewHolder(vh1, position)
            }
            REVIEW -> {
                val vh2 = viewHolder as ReviewViewHolder
                configureReviewViewHolder(vh2, position)
            }
        }
    }
    fun updateGames(reviews: List<Any>) {
        this.items = reviews
        notifyDataSetChanged()
    }
    /*inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gameRating: TextView = itemView.findViewById(R.id.game_rating_textview)
        val gameTitle: TextView = itemView.findViewById(R.id.game_title_textview)
        val gameReleaseDate: TextView = itemView.findViewById(R.id.game_release_date_textview)
        val gamePlatform: TextView = itemView.findViewById(R.id.game_platform_textview)
    }*/
    inner class RatingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var username: TextView =  itemView.findViewById(R.id.username_textview)
        var ratingBar: RatingBar = itemView.findViewById(R.id.rating_bar)
    }
    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var username: TextView = itemView.findViewById(R.id.username_textview)
        var review: TextView = itemView.findViewById(R.id.review_textview)
    }

    /*private fun configureDefaultViewHolder(vh: ReviewViewHolder, position: Int) {
        vh.getLabel().setText(items[position] as CharSequence)
    }*/

    private fun configureRatingViewHolder(vh1: RatingViewHolder, position: Int) {
        val rating = items[position] as UserRating
        if (rating != null) {
            vh1.username.text = rating.userName
            vh1.ratingBar.numStars = rating.rating.toInt()
        }
    }

    private fun configureReviewViewHolder(vh2: ReviewViewHolder, position: Int) {
        val review = items[position] as UserReview
        if (review != null) {
            vh2.username.text = review.userName
            vh2.review.text = review.review
        }
    }

}