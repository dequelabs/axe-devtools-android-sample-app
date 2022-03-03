package com.deque.mobile.axedevtoolssampleapp

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

private var counter: Int = 0

private val images = listOf(
    R.drawable.catalog_hero,
    R.drawable.hero,
    R.drawable.catalog_hero,
    R.drawable.hero
)

private val handler: Handler = Handler(Looper.getMainLooper())

@SuppressLint("NotifyDataSetChanged")
fun carouselScroll(carouselRv: RecyclerView, positionRv: RecyclerView) {
    if (counter == images.size) {
        counter = -1
    }
    handler.postDelayed({
        counter++
        toggleScrollable(carouselRv, true)

        positionRv.adapter?.notifyDataSetChanged()
        carouselRv.smoothScrollToPosition(counter)

        LinearSmoothScroller(carouselRv.context).targetPosition = counter

        handler.postDelayed({ toggleScrollable(carouselRv, false) }, 500)
        handler.postDelayed({ carouselScroll(carouselRv, positionRv) }, 1000)
    }, 4000)
}

fun carouselStop() {
    counter = 0
    handler.removeCallbacksAndMessages(null)
}

fun toggleScrollable(rv: RecyclerView, next: Boolean) {
    if (rv.scrollState == RecyclerView.SCROLL_STATE_IDLE) {
        (rv.layoutManager as CarouselLayoutManager).canScroll = next
    } else {
        rv.postDelayed({ toggleScrollable(rv, next) }, 500)
    }
}


class CarouselAdapter() : RecyclerView.Adapter<CarouselViewHolder>() {
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
                holder.active.visibility = View.VISIBLE
                holder.inactive.visibility = View.GONE
            }
            TYPE_INACTIVE -> {
                holder.active.visibility = View.GONE
                holder.inactive.visibility = View.VISIBLE
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