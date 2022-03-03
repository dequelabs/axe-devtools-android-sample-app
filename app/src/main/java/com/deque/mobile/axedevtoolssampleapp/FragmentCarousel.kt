package com.deque.mobile.axedevtoolssampleapp

import android.annotation.SuppressLint
import android.os.Bundle
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

private val images = listOf(
    R.drawable.catalog_hero,
    R.drawable.hero,
    R.drawable.catalog_hero,
    R.drawable.hero
)
private var counter = 0

class FragmentCarousel : Fragment() {

    lateinit var carouselRV: RecyclerView
    lateinit var positionRv: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_carousel, null)

        view.findViewById<Button>(R.id.next_button)
            .setOnClickListener { (activity as MainActivity).nextFragment.value = FragmentHome() }

        carouselRV = view.findViewById<RecyclerView>(R.id.carousel_rv)
        carouselRV.adapter = CarouselAdapter()
        carouselRV.layoutManager =
            CarouselLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        LinearSnapHelper().attachToRecyclerView(carouselRV)

        positionRv = view.findViewById<RecyclerView>(R.id.carousel_position_indicator_rv)
        positionRv.adapter = CarouselPositionAdapter()
        positionRv.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        carouselScroll()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun carouselScroll() {
        if (counter == images.size) {
            toggleScrollable(carouselRV, false)
            return
        }
        carouselRV.postDelayed({
            counter++
            toggleScrollable(carouselRV, true)

            positionRv.adapter?.notifyDataSetChanged()
            carouselRV.smoothScrollToPosition(counter)

            LinearSmoothScroller(carouselRV.context).targetPosition = counter

            carouselRV.postDelayed({toggleScrollable(carouselRV, false)}, 500)
            carouselRV.postDelayed({ carouselScroll() }, 1000)
        }, 4000)
    }

    private fun toggleScrollable(rv: RecyclerView, next: Boolean) {
        if (rv.scrollState == SCROLL_STATE_IDLE) {
            (rv.layoutManager as CarouselLayoutManager).canScroll = next
        } else {
            rv.postDelayed({ toggleScrollable(rv, next) }, 500)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        counter = 0
        carouselRV.removeCallbacks(null)
    }
}

class CarouselAdapter : RecyclerView.Adapter<CarouselViewHolder>() {
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
        return images.size
    }

}

class CarouselViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val image: ImageView = itemView.findViewById(R.id.carousel_image)
}

class CarouselLayoutManager(context: FragmentActivity?, direction: Int, reversed: Boolean) :
    LinearLayoutManager(context, direction, reversed) {
    var canScroll = false
    override fun canScrollHorizontally(): Boolean {
        return canScroll && super.canScrollHorizontally()
    }
}

class CarouselPositionAdapter : RecyclerView.Adapter<CarouselPositionViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (position == counter) TYPE_ACTIVE
        else if(counter >= images.size && position == images.size - 1) TYPE_ACTIVE
        else TYPE_INACTIVE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselPositionViewHolder {
        return CarouselPositionViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_carousel_position_dot, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CarouselPositionViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_ACTIVE -> {
                holder.active.visibility = VISIBLE
                holder.inactive.visibility = GONE
            }
            TYPE_INACTIVE -> {
                holder.active.visibility = GONE
                holder.inactive.visibility = VISIBLE
            }
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }

    companion object {
        const val TYPE_ACTIVE = 0
        const val TYPE_INACTIVE = 1
    }
}

class CarouselPositionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val active: ImageView = itemView.findViewById(R.id.active_dot)
    val inactive: ImageView = itemView.findViewById(R.id.inactive_dot)
}