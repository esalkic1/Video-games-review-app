package com.example.spirala

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.findFragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {

    private lateinit var favoriteGames: RecyclerView
    private lateinit var favoriteGamesAdapter: GameListAdapter
    private var favoriteGamesList =  GameData.getAll()
    private lateinit var game: Game

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view =  inflater.inflate(R.layout.fragment_home, container, false)
        favoriteGames = view.findViewById(R.id.game_list)
        favoriteGames.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
        favoriteGamesAdapter = GameListAdapter(arrayListOf()) { game -> showGameDetails(game) }
        favoriteGames.adapter = favoriteGamesAdapter
        favoriteGamesAdapter.updateGames(favoriteGamesList)

        val bundle = arguments
        if (bundle != null) {
            game = GameData.getDetails(bundle.getString("game_title", ""))!!
            val orientation = this.resources.configuration.orientation
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                bottomNavigationView = requireActivity().findViewById(R.id.bottom_nav)
                val detailsItem: BottomNavigationItemView =
                    bottomNavigationView.findViewById(R.id.gameDetailsItem)
                bottomNavigationView.menu.getItem(1).isEnabled = true
                detailsItem.setOnClickListener {
                    requireView().findNavController()
                        .navigate(R.id.action_homeFragment_to_gameDetailsFragment, bundle)
                }
            }
        }

        return view;
    }
    private fun showGameDetails(game: Game) {
        val orientation = this.resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            val bundle = bundleOf("game_title" to game.title)
            requireView().findNavController()
                .navigate(R.id.action_homeFragment_to_gameDetailsFragment, bundle)
        }
        else{
            val bundle = bundleOf("game_title" to game.title)
            val gameDetailsFragment = GameDetailsFragment()
            gameDetailsFragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_landscape, gameDetailsFragment).commit()
        }
    }

}