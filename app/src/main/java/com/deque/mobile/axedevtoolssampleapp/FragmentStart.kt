package com.deque.mobile.axedevtoolssampleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class FragmentStart : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_start, null)

        view.findViewById<Button>(R.id.start_button).setOnClickListener {
            (activity as MainActivity).nextFragment.value = FragmentCarousel()
        }

        return view
    }
}
