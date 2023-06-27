package ba.etf.rma23.projekat

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository
import ba.etf.rma23.projekat.data.repositories.GamesRepository
import com.example.spirala.R
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import ba.etf.rma23.projekat.data.repositories.*
import kotlinx.coroutines.*

class HomeFragment : Fragment() {

    private lateinit var favoriteGames: RecyclerView
    private lateinit var favoriteGamesAdapter: GameListAdapter
    private var favoriteGamesList = GameData.getAll()
    private lateinit var game: Game
    private lateinit var searchButton: Button
    private lateinit var searchText: EditText
    private lateinit var sortButton: Button

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        context?.let {
            getFavoriteGames()
        }
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
        searchButton = view.findViewById(R.id.search_button)
        searchText = view.findViewById(R.id.search_query_edittext)
        arguments?.getString("search")?.let {
            searchText.setText(it)
        }

        searchButton.setOnClickListener{
            onClick();
        }

        sortButton = view.findViewById(R.id.sort_button)
        sortButton.setOnClickListener{
            sortGames()
        }

        //vezano za klik na home
        bottomNavigationView = requireActivity().findViewById(R.id.bottom_nav)
        val homeItem: BottomNavigationItemView =
            bottomNavigationView.findViewById(R.id.homeItem)
        bottomNavigationView.menu.getItem(0).isEnabled
        homeItem.setOnClickListener{
            getFavoriteGames()
        }

        val bundle = arguments
        if (bundle != null) {
            game = getGame(bundle.getInt("game_id"))
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
            val bundle = bundleOf("game_id" to game.id)
            requireView().findNavController()
                .navigate(R.id.action_homeFragment_to_gameDetailsFragment, bundle)
        }
        else{
            val bundle = bundleOf("game_id" to game.id)
            val gameDetailsFragment = GameDetailsFragment()
            gameDetailsFragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_landscape, gameDetailsFragment).commit()
        }
    }

    fun getGame(id: Int): Game {
        runBlocking {
            try {
                val foundGame = CoroutineScope(Dispatchers.IO).async {
                    GamesRepository.getGamesByID(id)
                }
                val found = foundGame.await()
                if (found.isNotEmpty()) game = found[0]
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return game
    }

    private fun sortGames() {
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            try {
                val result = GamesRepository.sortGames()
                when (result) {
                    is List<Game> -> onSortSuccess(result)
                    else -> onSortError()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onSortError()
            }
        }
    }

    fun onSortSuccess(games: List<Game>) {
        val toast = Toast.makeText(context, "Sort done", Toast.LENGTH_SHORT)
        toast.show()
        favoriteGamesAdapter.updateGames(games)
    }

    private fun onSortError() {
        val toast = Toast.makeText(context, "Sort error", Toast.LENGTH_SHORT)
        toast.show()
    }


    private fun onClick() {
        val toast = Toast.makeText(context, "Search start", Toast.LENGTH_SHORT)
        toast.show()
        search(searchText.text.toString())
    }
    fun search(query: String) {
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        // Create a new coroutine on the UI thread
        scope.launch {
            try {
                // Vrti se poziv servisa i suspendira se rutina dok se `withContext` ne zavrsi
                var result: List<Game>
                if (AccountGamesRepository.userAge > 17 || AccountGamesRepository.userAge == 0) {
                    result = GamesRepository.getGamesByName(query)
                } else {
                    result = GamesRepository.getGamesSafe(query)
                }
                // Prikaze se rezultat korisniku na glavnoj niti
                when (result) {
                    is List<Game> -> searchDone(result)
                    else -> onSearchError()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onSearchError()
            }
        }
    }

    fun searchDone(games:List<Game>){
        val toast = Toast.makeText(context, "Search done", Toast.LENGTH_SHORT)
        toast.show()
        favoriteGamesAdapter.updateGames(games)
    }
    fun onSearchError() {
        if (context != null) {
            val toast = Toast.makeText(context, "Search error", Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    fun getFavoriteGames() {
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        // Create a new coroutine on the UI thread
        scope.launch {
            try {
                // Opcija 1
                val result = AccountGamesRepository.getSavedGames()
                // Display result of the network request to the user
                when (result) {
                    is List<Game> -> onSuccess(result)
                    else -> onError()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onError()
            }
        }
    }

    fun onSuccess(games:List<Game>){
        val toast = Toast.makeText(context, "Games found", Toast.LENGTH_SHORT)
        toast.show()
        favoriteGamesAdapter.updateGames(games)
    }
    fun onError() {
        if(context != null) {
            val toast = Toast.makeText(context, "Search error", Toast.LENGTH_SHORT)
            toast.show()
        }
    }
}

