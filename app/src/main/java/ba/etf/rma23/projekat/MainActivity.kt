package ba.etf.rma23.projekat

import android.content.Context
import android.content.res.Configuration
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.test.platform.app.InstrumentationRegistry
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository
import ba.etf.rma23.projekat.data.repositories.GameReviewsRepository
import ba.etf.rma23.projekat.data.repositories.GamesRepository
import ba.etf.rma23.projekat.data.repositories.IGDBApiConfig
import com.example.spirala.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        //getFavorites(this)
        }

    /*fun getFavorites(context: Context){
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        // Create a new coroutine on the UI thread
        scope.launch{

            // Make the network call and suspend execution until it finishes
            val result = GameReviewsRepository.getOfflineReviews(context)

            // Display result of the network request to the user
            when (result) {
                is List<GameReview> -> {
                    for (i in result.indices){
                        println("BRAVO MAJSTORE" + result.get(i).igdb_id)
                        println("online: " + result.get(i).online)
                    }
                }
            }
        }
    }*/






}