package ba.etf.rma23.projekat

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository
import ba.etf.rma23.projekat.data.repositories.GameReviewsRepository
import ba.etf.rma23.projekat.data.repositories.GamesRepository
import com.bumptech.glide.Glide
import com.example.spirala.R
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*

private lateinit var reviews: RecyclerView
private lateinit var reviewsAdapter: ReviewListAdapter
private lateinit var reviewsList: ArrayList<Any>


class GameDetailsFragment : Fragment() {

    private lateinit var game: Game
    private lateinit var cover: ImageView
    private lateinit var title: TextView
    private lateinit var platform: TextView
    private lateinit var releaseDate: TextView
    private lateinit var esrbRating: TextView
    private lateinit var developer: TextView
    private lateinit var publisher: TextView
    private lateinit var genre: TextView
    private lateinit var description: TextView

    private lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var saveGameButton: Button
    private lateinit var removeGameButton: Button
    private lateinit var addReviewButton: Button

    private lateinit var reviewText: EditText
    private lateinit var ratingSpinner: Spinner

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_game_details, container, false)
        cover = view.findViewById(R.id.cover_imageview)
        title = view.findViewById(R.id.item_title_textview)
        platform = view.findViewById(R.id.platform_textview)
        releaseDate = view.findViewById(R.id.release_date_textview)
        esrbRating = view.findViewById(R.id.esrb_rating_textview)
        developer = view.findViewById(R.id.developer_textview)
        publisher = view.findViewById(R.id.publisher_textview)
        genre = view.findViewById(R.id.genre_textview)
        description = view.findViewById(R.id.description_textview)


        val bundle = arguments
        if (bundle != null) {
            runBlocking {
                game = getGame(bundle.getInt("game_id"))
            }
                //GameData.getDetails(bundle.getString("game_title", ""))!!
            populateDetails()
        }
        else{    // ovo ostavljam ovako
            game = GameData.getDetails("FIFA 23")!!
            populateDetails()
        }

        //recyclerView
        //reviewsList = GameData.getSortedImpressions(game.title)
        reviewsList = ArrayList()
        reviews = view.findViewById(R.id.review_list)
        reviews.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
        reviewsAdapter = ReviewListAdapter(reviewsList)
        reviews.adapter = reviewsAdapter

// Call getReviewsById to fetch and populate the reviews list
        GlobalScope.launch {
            getReviewsById(game.id)
        }

        val orientation = this.resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            bottomNavigationView = requireActivity().findViewById(R.id.bottom_nav)
            val homeItem: BottomNavigationItemView =
                bottomNavigationView.findViewById(R.id.homeItem)
            homeItem.setOnClickListener {
                requireView().findNavController()
                    .navigate(R.id.action_gameDetailsItem_to_homeItem2, bundle)
            }
        }

        saveGameButton = view.findViewById(R.id.button_save_game)
        removeGameButton = view.findViewById(R.id.button_remove_game)

        saveGameButton.setOnClickListener {
            val toast = Toast.makeText(context, "Saving...", Toast.LENGTH_SHORT)
            toast.show()
            saveGame()
        }

        removeGameButton.setOnClickListener{
            val toast = Toast.makeText(context, "Removing...", Toast.LENGTH_SHORT)
            toast.show()
            removeGame()
        }

        addReviewButton = view.findViewById(R.id.button_add_review)
        reviewText = view.findViewById(R.id.editText_review)
        ratingSpinner = view.findViewById(R.id.spinner_rating)

        addReviewButton.setOnClickListener{
            val review: String = reviewText.text.toString()
            val rating: String = ratingSpinner.selectedItem.toString()
            sendReviewApi(requireContext(), review, rating)
        }


            return view
        }


    private fun saveGame() {
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            var alreadySaved = false
            val result = AccountGamesRepository.getSavedGames()
            for (i in result.indices){
                if (result.get(i).id == game.id) alreadySaved = true
            }
            if(!alreadySaved) {
                AccountGamesRepository.saveGame(game)
                 onSaveSuccess(game)
            }
            else {
                onSaveError()
            }
        }
    }

    private fun removeGame() {
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            var savedBefore = false
            val result = AccountGamesRepository.getSavedGames()
            for (i in result.indices){
                if (result.get(i).id == game.id) savedBefore = true
            }
            if(savedBefore) {
                AccountGamesRepository.removeGame(game.id)
                onRemoveSuccess(game)
            }
            else {
                onRemoveError()
            }
        }
    }

    private fun onRemoveSuccess(game: Game) {
        val toast = Toast.makeText(context, "Game removed successfully", Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun onRemoveError() {
        val toast = Toast.makeText(context, "Game not saved before", Toast.LENGTH_SHORT)
        toast.show()
    }


    private fun onSaveSuccess(result: Game) {
        val toast = Toast.makeText(context, "Game saved successfully", Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun onSaveError() {
        val toast = Toast.makeText(context, "Game already saved before", Toast.LENGTH_SHORT)
        toast.show()
    }


    fun getGame(id: Int): Game{
        runBlocking {
            val foundGame = CoroutineScope(Dispatchers.IO).async {
                GamesRepository.getGamesByID(id)
            }
            val found = foundGame.await()
            if (found.isNotEmpty()) game = found.get(0)
        }
        return game
    }



    fun sendReviewApi(context: Context, review: String, rating: String){
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            val result = GameReviewsRepository.sendReview(context, GameReview(rating.toInt(), review, game.id, false, "", ""))
            if (result){
                val toast = Toast.makeText(context, "Review sent", Toast.LENGTH_SHORT)
                toast.show()
            }
            else{
                val toast = Toast.makeText(context, "Review saved to database", Toast.LENGTH_SHORT)
                toast.show()
            }
        }
    }


    private suspend fun getReviewsById(id: Int) {
        val list = withContext(Dispatchers.IO) {
            GameReviewsRepository.getReviewsForGame(id)
        }
        for (i in list.indices) {
            val ts = list[i].timestamp?.toLong()
            val rating = list[i].rating?.toDouble()
            if (list[i].rating != null) reviewsList.add(UserRating(list[i].student!!, ts!!, rating!!))
            if (list[i].review != null) reviewsList.add(UserReview(list[i].student!!, ts!!, list[i].review!!))
        }
        withContext(Dispatchers.Main) {
            reviewsAdapter.updateGames(reviewsList)
        }
    }



    /*override fun onBackPressed() {
        openNewActivity()
    }*/

    /*private fun openNewActivity() {
        val intent = Intent(this, HomeActivity::class.java).apply {
            putExtra("game_title", game.title)
        }
        startActivity(intent)
    }*/

    private fun populateDetails() {
        title.text = game.title
        platform.text = game.platform
        releaseDate.text = game.releaseDate
        esrbRating.text = game.esrbRating
        developer.text = game.developer
        publisher.text = game.publisher
        genre.text = game.genre
        description.text = game.description

        val context: Context = cover.context
        /*var id: Int = context.resources
            .getIdentifier(game.coverImage, "drawable", context.packageName)
        if (id===0) id=context.resources
            .getIdentifier("logo_icon", "drawable", context.packageName)
        cover.setImageResource(id)*/
        if (game.coverImage != "") {
            Glide.with(context)
                .load("https:" + game.coverImage)
                .centerCrop()
                .into(cover)
        }
    }
}