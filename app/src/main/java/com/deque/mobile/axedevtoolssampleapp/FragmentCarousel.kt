package com.deque.mobile.axedevtoolssampleapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE



class FragmentCarousel : Fragment() {

    lateinit var carouselRV: RecyclerView
    lateinit var positionRv: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_carousel, null)

        carouselRV = view.findViewById(R.id.carousel_rv)
        carouselRV.adapter = CarouselAdapter()
        carouselRV.layoutManager =
            CarouselLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        LinearSnapHelper().attachToRecyclerView(carouselRV)

        positionRv = view.findViewById(R.id.carousel_position_indicator_rv)
        positionRv.adapter = CarouselPositionAdapter()
        positionRv.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        return view
    }

    override fun onResume() {
        super.onResume()
        carouselScroll(carouselRV, positionRv)
    }
}