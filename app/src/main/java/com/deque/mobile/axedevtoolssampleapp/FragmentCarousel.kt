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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_carousel, null)

        view.findViewById<Button>(R.id.next_button)
            .setOnClickListener { (activity as MainActivity).nextFragment.value = FragmentHome() }

        val carouselRV = view.findViewById<RecyclerView>(R.id.carousel_rv)
        carouselRV.adapter = CarouselAdapter()
        carouselRV.layoutManager =
            CarouselLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        LinearSnapHelper().attachToRecyclerView(carouselRV)

        val positionRv = view.findViewById<RecyclerView>(R.id.carousel_position_indicator_rv)
        positionRv.adapter = CarouselPositionAdapter()
        positionRv.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rv = view.findViewById<RecyclerView>(R.id.carousel_rv)
        val posRv = view.findViewById<RecyclerView>(R.id.carousel_position_indicator_rv)
        carouselScroll(rv, posRv)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun carouselScroll(rv: RecyclerView, posRv: RecyclerView) {
        if (counter == images.size) {
            toggleScrollable(rv, false)
            return
        }
        rv.postDelayed({
            counter++
            toggleScrollable(rv, true)

            posRv.adapter?.notifyDataSetChanged()
            rv.smoothScrollToPosition(counter)

            rv.postDelayed({toggleScrollable(rv, false)}, 500)
            rv.postDelayed({ carouselScroll(rv, posRv) }, 4000)
        }, 1000)
    }

    private fun toggleScrollable(rv: RecyclerView, next: Boolean) {
        if (rv.scrollState == SCROLL_STATE_IDLE) {
            (rv.layoutManager as CarouselLayoutManager).canScroll = next
        } else {
            rv.postDelayed({ toggleScrollable(rv, next) }, 500)
        }
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