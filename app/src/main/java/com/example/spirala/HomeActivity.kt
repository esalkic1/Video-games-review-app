package com.example.spirala

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private lateinit var favoriteGames: RecyclerView
private lateinit var favoriteGamesAdapter: GameListAdapter
private var favoriteGamesList =  GameData.getAll()

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
        favoriteGamesAdapter = GameListAdapter(listOf())
        favoriteGames.adapter = favoriteGamesAdapter
        favoriteGamesAdapter.updateGames(favoriteGamesList)


        }
}