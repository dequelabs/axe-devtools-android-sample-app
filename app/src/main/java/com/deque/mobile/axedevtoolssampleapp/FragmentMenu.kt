package com.deque.mobile.axedevtoolssampleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment

class FragmentMenu : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, null)
//        val composeView = view.findViewById<ComposeView>(R.id.fragment_menu_compose_view)
//        composeView.apply {
//            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
//            setContent {
//                MenuScreen()
//            }
//        }


        return view
    }
}
