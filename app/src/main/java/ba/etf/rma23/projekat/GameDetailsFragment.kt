package ba.etf.rma23.projekat

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository
import ba.etf.rma23.projekat.data.repositories.GamesRepository
import com.bumptech.glide.Glide
import com.example.spirala.R
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*

private lateinit var reviews: RecyclerView
private lateinit var reviewsAdapter: ReviewListAdapter
private lateinit var reviewsList: List<Any>


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
        reviewsList = GameData.getSortedImpressions(game.title)
        reviews = view.findViewById(R.id.review_list)
        reviews.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
        reviewsAdapter = ReviewListAdapter(arrayListOf())
        reviews.adapter = reviewsAdapter
        reviewsAdapter.updateGames(reviewsList)

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