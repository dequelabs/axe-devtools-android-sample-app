package com.deque.mobile.axedevtoolssampleapp

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FragmentCarousel : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_carousel, null)

        view.findViewById<Button>(R.id.next_button)
            .setOnClickListener { (activity as MainActivity).nextFragment.value = FragmentStart() }

        val carouselRV = view.findViewById<RecyclerView>(R.id.carousel_rv)
        carouselRV.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        carouselRV.adapter = CarouselAdapter()


        return view
    }

}

class CarouselAdapter : RecyclerView.Adapter<CarouselViewHolder>() {
    private val images = listOf(R.drawable.hero, R.drawable.hero, R.drawable.hero, R.drawable.hero)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_carousel_image, parent, false)
        return CarouselViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val res = holder.image.resources
        holder.image.setImageDrawable(ResourcesCompat.getDrawable(res, images[position], null))
    }

    override fun getItemCount(): Int {
        return 4
    }

}

class CarouselViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val image: ImageView = itemView.findViewById(R.id.carousel_image)
}
