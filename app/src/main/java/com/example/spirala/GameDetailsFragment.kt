package com.example.spirala

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView

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
            game = GameData.getDetails(bundle.getString("game_title", ""))!!
            populateDetails()
        }
        else{
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



            return view
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
        var id: Int = context.resources
            .getIdentifier(game.coverImage, "drawable", context.packageName)
        if (id===0) id=context.resources
            .getIdentifier("logo_icon", "drawable", context.packageName)
        cover.setImageResource(id)
    }
}