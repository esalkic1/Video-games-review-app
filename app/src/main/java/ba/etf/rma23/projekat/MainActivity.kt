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

private val countNotOnline =
    "SELECT COUNT(*) AS broj_reviews FROM gamereview WHERE online=false"

private lateinit var db: SQLiteDatabase

private fun executeCountAndCheck(query: String, column: String, value: Long) {
    var rezultat = db.rawQuery(query, null)
    rezultat.moveToFirst()
    var brojOdgovora = rezultat.getLong(0)
    MatcherAssert.assertThat(brojOdgovora, CoreMatchers.`is`(CoreMatchers.equalTo(value)))
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //writeDB(this)
        //sendReviewApi(this)
        //getFavorites(this)
        //sendOfflineReviewsApi(this)
        //getReviewsById(168537)
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
        //doSomething(this)
        getFavorites(this)
        }

    fun doSomething(context: Context){
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            var rez =
                GameReviewsRepository.sendReview(
                    context,
                    GameReview(3, "dobro", 250, false, "", "")
                )
            println(rez)
            val query = "SELECT COUNT(*) AS broj_reviews FROM gamereview WHERE online=false"
            val baza: String
            baza = context.databaseList().minBy { x -> x.length }
            db = SQLiteDatabase.openDatabase(
                context.getDatabasePath(baza).absolutePath,
                null,
                SQLiteDatabase.OPEN_READONLY
            )
            var rezultat = db.rawQuery(query, null)
            rezultat.moveToFirst()
            var brojOdgovora = rezultat.getLong(0)
            println(brojOdgovora)
        }
    }

    fun writeDB(context: Context){
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch{
            val result = GameReviewsRepository.writeReview(context, GameReview(4, "Easy to play", 85450, false, "", ""))
            println(result)
            when (result) {
                is String -> onSuccess1(result)
                else-> onError()
            }
        }
    }

    fun onSuccess1(message:String){
        val toast = Toast.makeText(applicationContext, "Spaseno", Toast.LENGTH_SHORT)
        toast.show()
    }
    fun onError() {
        val toast = Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT)
        toast.show()
    }

    fun getFavorites(context: Context){
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
    }

    fun sendReviewApi(context: Context){
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            val result = GameReviewsRepository.sendReview(context, GameReview(1, "Loše", 176032, true, "", ""))
            println(result)
        }
    }

    fun sendOfflineReviewsApi(context: Context){
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            val result = GameReviewsRepository.sendOfflineReviews(context)
            println("Poslano: " + result)
        }
    }

    fun getReviewsById(id: Int){
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            val result = GameReviewsRepository.getReviewsForGame(id)
            for (i in result.indices){
                println("Za: " + result.get(i).igdb_id)
                println("Evo: " + result.get(i).review)
                println("Evo i rating: " + result.get(i).rating)
            }
        }
    }
}