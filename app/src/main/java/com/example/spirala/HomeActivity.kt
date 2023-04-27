package com.example.spirala

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
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
        }

}