package com.deque.mobile.axedevtoolssampleapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import com.deque.mobile.axedevtoolssampleapp.ui.Destinations
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        navController.graph = navController.createGraph(
            startDestination = Destinations.Catalog
        ) {
            fragment<FragmentCatalog, Destinations.Catalog> {
                label = "Catalog"
            }

            fragment<FragmentCart, Destinations.Cart> {
                label = "Cart"
            }

            fragment<FragmentMenu, Destinations.Menu> {
                label = "Menu"
            }
        }


        val bottomNav: NavigationBarView = findViewById(R.id.bottom_nav_bar)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.catalog -> {
                    navController.navigate(Destinations.Catalog)
                    true
                }

                R.id.cart -> {
                    navController.navigate(Destinations.Cart)
                    true
                }

                R.id.menu -> {
                    navController.navigate(Destinations.Menu)
                    true
                }

                else -> {
                    throw IllegalArgumentException("That nav option does not exist.")
                }
            }
        }

        bottomNav.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.catalog, R.id.cart, R.id.menu -> {}
            }
        }

    }

}