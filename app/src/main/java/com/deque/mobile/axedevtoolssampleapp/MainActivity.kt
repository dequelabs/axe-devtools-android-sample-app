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
    val nextFragment = MutableStateFlow<Fragment>(FragmentStart())

    companion object {
        var axe: AxeDevTools? = null
        init {
            /***
             * Hello! Thank you for checking out the axe DevTools for Android sample app.
             * Please provide your API key in the build.gradle file in the beginning of the android block.
             * Without the API key, tests will time out and fail.
             ***/
            if (!isRunningTest) axe = AxeDevTools()
            axe?.connect(BuildConfig.AXE_DEVTOOLS_APIKEY)

            /***
             * If you prefer to use user/password credentials feel free to pass those values
             * into axe.connect(username, password) as provided as a comment below.
             */
//          axe?.connect(username = "", password = "")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        launchNextFragment(nextFragment.value)

        val bottomNav: NavigationBarView = findViewById(R.id.bottom_nav_bar)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    nextFragment.value = FragmentHome()
                    true
                }
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
                R.id.home, R.id.catalog, R.id.cart, R.id.menu -> {}
            }
        }

        lifecycleScope.launchWhenStarted {
            nextFragment.collectLatest {
                launchNextFragment(it)
            }
        }

        axe?.showA11yFAB(this)
    }

    private fun launchNextFragment(fragment: Fragment) {
        val bottomNavBar: NavigationBarView = findViewById(R.id.bottom_nav_bar)
        val startFrame: FrameLayout = findViewById(R.id.start_frame)
        val mainFrame: FrameLayout = findViewById(R.id.main_frame)

        carouselStop()

        if (fragment is FragmentStart || fragment is FragmentCarousel) {
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
        if (supportFragmentManager.backStackEntryCount <= 1) {
            supportFragmentManager.popBackStack()
            launchNextFragment(FragmentStart())
        } else {
            val bottomNav: NavigationBarView = findViewById(R.id.bottom_nav_bar)
            val nextPop = supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 2)

            when(nextPop.name) {
                FragmentHome::class.simpleName -> bottomNav.selectedItemId = R.id.home
                FragmentCatalog::class.simpleName -> bottomNav.selectedItemId = R.id.catalog
                FragmentCart::class.simpleName -> bottomNav.selectedItemId = R.id.cart
                FragmentMenu::class.simpleName -> bottomNav.selectedItemId = R.id.menu
            }

            supportFragmentManager.popBackStack()
            super.onBackPressed()
        }
    }
}

val isRunningTest : Boolean by lazy {
    try {
        Class.forName("androidx.test.espresso.Espresso")
        true
    } catch (e: ClassNotFoundException) {
        false
    }
}