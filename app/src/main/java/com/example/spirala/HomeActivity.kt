package com.example.spirala

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private lateinit var favoriteGames: RecyclerView
private lateinit var favoriteGamesAdapter: GameListAdapter
private var favoriteGamesList =  GameData.getAll()

private lateinit var detailsButton: Button

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        favoriteGames = findViewById(R.id.game_list)
        favoriteGames.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        favoriteGamesAdapter = GameListAdapter(arrayListOf()) { game -> showGameDetails(game) }
        favoriteGames.adapter = favoriteGamesAdapter
        favoriteGamesAdapter.updateGames(favoriteGamesList)

        detailsButton = findViewById(R.id.details_button)
        val extras = intent.extras
        if (extras!=null){
            detailsButton.isEnabled = true
            detailsButton.isClickable = false
        }
        detailsButton.setOnClickListener(){
            if (extras != null) {
                var game = GameData.getDetails(extras.getString("game_title", ""))!!
                showGameDetails(game)
            } else {
                finish()
            }
        }
        }

    private fun showGameDetails(game: Game) {
        val intent = Intent(this, GameDetailsActivity::class.java).apply {
            putExtra("game_title", game.title)
        }
        startActivity(intent)
    }
}