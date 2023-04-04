package com.example.spirala

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


private lateinit var reviews: RecyclerView
private lateinit var reviewsAdapter: ReviewListAdapter
private lateinit var reviewsList: List<Any>
private lateinit var homeButton: Button

class GameDetailsActivity : AppCompatActivity() {
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_details)
        cover = findViewById(R.id.cover_imageview)
        title = findViewById(R.id.game_title_textview)
        platform = findViewById(R.id.platform_textview)
        releaseDate = findViewById(R.id.release_date_textview)
        esrbRating = findViewById(R.id.esrb_rating_textview)
        developer = findViewById(R.id.developer_textview)
        publisher = findViewById(R.id.publisher_textview)
        genre = findViewById(R.id.genre_textview)
        description = findViewById(R.id.description_textview)
        val extras = intent.extras
        if (extras != null) {
            game = GameData.getDetails(extras.getString("game_title",""))!!
            populateDetails()
        } else {
            finish()
        }

        //recyclerView
        reviewsList = GameData.getSortedImpressions(game.title)
        reviews = findViewById(R.id.review_list)
        reviews.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        reviewsAdapter = ReviewListAdapter(arrayListOf())
        reviews.adapter = reviewsAdapter
        reviewsAdapter.updateGames(reviewsList)

        homeButton = findViewById(R.id.home_button)
        homeButton.setOnClickListener(){
            openNewActivity()
        }

    }

    override fun onBackPressed() {
        openNewActivity()
    }

    private fun openNewActivity() {
        val intent = Intent(this, HomeActivity::class.java).apply {
            putExtra("game_title", game.title)
        }
        startActivity(intent)
    }

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

