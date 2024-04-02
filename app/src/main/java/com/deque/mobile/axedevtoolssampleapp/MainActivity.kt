package com.deque.mobile.axedevtoolssampleapp

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.deque.mobile.devtools.AxeDevTools
import com.google.android.material.navigation.NavigationBarView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity() {
    private val nextFragment = MutableStateFlow<Fragment>(FragmentCatalog())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        launchNextFragment(nextFragment.value)

        val bottomNav: NavigationBarView = findViewById(R.id.bottom_nav_bar)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.catalog -> {
                    nextFragment.value = FragmentCatalog()
                    true
                }

                R.id.cart -> {
                    nextFragment.value = FragmentCart()
                    true
                }

                R.id.menu -> {
                    nextFragment.value = FragmentMenu()
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

        lifecycleScope.launchWhenStarted {
            nextFragment.collectLatest {
                launchNextFragment(it)
            }
        }
    }

    private fun launchNextFragment(fragment: Fragment) {
        val bottomNavBar: NavigationBarView = findViewById(R.id.bottom_nav_bar)
        val startFrame: FrameLayout = findViewById(R.id.start_frame)
        val mainFrame: FrameLayout = findViewById(R.id.main_frame)

        carouselStop()

        if (fragment is FragmentCarousel) {
            bottomNavBar.visibility = GONE
            startFrame.visibility = VISIBLE
            mainFrame.visibility = GONE

            mainFrame.removeAllViews()
            supportFragmentManager.beginTransaction().replace(R.id.start_frame, fragment).commit()
        } else {
            bottomNavBar.visibility = VISIBLE
            startFrame.visibility = GONE
            mainFrame.visibility = VISIBLE

            startFrame.removeAllViews()
            supportFragmentManager.beginTransaction().replace(R.id.main_frame, fragment)
                .addToBackStack(fragment::class.simpleName).commit()
        }
    }

    override fun onBackPressed() {
        val bottomNav: NavigationBarView = findViewById(R.id.bottom_nav_bar)
        val nextPop =
            supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 2)

        when (nextPop.name) {
            FragmentCatalog::class.simpleName -> bottomNav.selectedItemId = R.id.catalog
            FragmentCart::class.simpleName -> bottomNav.selectedItemId = R.id.cart
            FragmentMenu::class.simpleName -> bottomNav.selectedItemId = R.id.menu
        }

        supportFragmentManager.popBackStack()
        super.onBackPressed()
    }
}

val isRunningTest: Boolean by lazy {
    try {
        Class.forName("androidx.test.espresso.Espresso")
        true
    } catch (e: ClassNotFoundException) {
        false
    }
}