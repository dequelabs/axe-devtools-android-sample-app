package com.deque.mobile.axedevtoolssampleapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find the NavHostFragment using its ID. This is the recommended way to
        // retrieve the NavController in an Activity.
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        // Find the BottomNavigationView
        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_nav_bar)

        // Set up the BottomNavigationView with the NavController.
        // This will automatically handle navigation when bottom nav items are tapped
        // and correctly update the selected item when navigating.
        bottomNav.setupWithNavController(navController)
    }

    // It's good practice to also handle the Up button correctly with your NavController.
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}