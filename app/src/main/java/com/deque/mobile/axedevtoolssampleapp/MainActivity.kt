package com.deque.mobile.axedevtoolssampleapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity() {
    val nextFragment = MutableStateFlow<Fragment>(FragmentStart())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        launchNextFragment(nextFragment.value)

        lifecycleScope.launchWhenStarted {
            nextFragment.collectLatest {
                launchNextFragment(it)
            }
        }
    }

    private fun launchNextFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.main_frame, fragment).commit()
    }
}