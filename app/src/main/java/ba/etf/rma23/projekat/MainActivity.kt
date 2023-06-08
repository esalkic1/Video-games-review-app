package ba.etf.rma23.projekat

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository
import ba.etf.rma23.projekat.data.repositories.GamesRepository
import ba.etf.rma23.projekat.data.repositories.IGDBApiConfig
import com.example.spirala.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*val config: Configuration = baseContext.resources.configuration
        CoroutineScope(Job() + Dispatchers.Main).launch {
            /*val response = AccountGamesRepository.getSavedGames()
            val a = response.size
            println("VELICINA: " + a)
            val ime = response.get(0).title
            println("IME " + ime)
            val platform = response.get(0).platform
            println("PLATFORMA " + platform)
            val id = response.get(0).id
            println("ID: " + id)
            val publisher = response.get(0).publisher
            println("PUBLISHER: "+publisher)
            val developer = response.get(0).developer
            println("DEVELOPER: " +developer)
            val date = response.get(0).releaseDate
            println("RELEASE DATE: " + date)*/
            /*val response = AccountGamesRepository.getGamesContainingString("leAgUe")
            for (i in response.indices) {
                val name = response.get(i).title
                println("NASLOV: " + name)
            }
            val response = AccountGamesRepository.removeGame(228509)
            println("VRACA: " + response)*/
            /*val resp = GamesRepository.getGamesByID(56250)
            val response = AccountGamesRepository.saveGame(resp.get(0))
            println("Cup critter treba: " + response.title)*/
            /*AccountGamesRepository.setAge(8);
            val response = GamesRepository.getGamesSafe("assassin")
            for (i in response.indices){ println(response.get(i).title)
                println(response.get(i).esrbRating)}*/
            AccountGamesRepository.saveGame(GamesRepository.getGamesByID(56250).get(0))
            val response = GamesRepository.sortGames()
            for (i in response.indices){
                println(response.get(i).title)
            }

        }
    }*/
        setContentView(R.layout.activity_home)
        val game = GameData.getDetails("FIFA 23")
        val orientation = this.resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController
            val navView: BottomNavigationView = findViewById(R.id.bottom_nav)
            navView.setupWithNavController(navController)
        }
        else{
            val navHostFragment2 = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_landscape) as NavHostFragment
            val navController = navHostFragment2.navController
        }
        }
}