package com.deque.mobile.axedevtoolssampleapp

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.deque.mobile.axedevtoolssampleapp.FragmentHome.Companion.IS_FAV
import com.deque.mobile.axedevtoolssampleapp.FragmentHome.Companion.NOT_FAV
import kotlin.random.Random

class FragmentHome : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, null)

        val mostPopularRv = view.findViewById<RecyclerView>(R.id.popular_rv)
        val collectionRv = view.findViewById<RecyclerView>(R.id.collection_rv)
        val recommendedRv = view.findViewById<RecyclerView>(R.id.recommended_rv)

        mostPopularRv.layoutManager = GridLayoutManager(activity, 2)
        collectionRv.layoutManager = LinearLayoutManager(activity)
        recommendedRv.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        mostPopularRv.adapter = PopularAdapter()
        collectionRv.adapter = CollectionAdapter()
        recommendedRv.adapter = RecommendedAdapter()

        LinearSnapHelper().attachToRecyclerView(recommendedRv)

        return view
    }

    companion object {
        const val NOT_FAV = "not fav"
        const val IS_FAV = "is fav"
    }
}

private fun setPortraitInventoryListeners(holder: PortraitInventoryViewHolder, @DrawableRes drawableRes: Int) {
    holder.image.setImageDrawable(
        ResourcesCompat.getDrawable(
            holder.image.resources,
            drawableRes,
            null
        )
    )
    holder.price.text = getPriceString(holder.price.resources)

    holder.favButton.setOnClickListener { toggleFav(holder.favButton) }
    holder.favButton.tag = NOT_FAV

    holder.bagButton.setOnClickListener { toggleBag(holder.bagButton, holder.bagButtonFull) }
    holder.bagButtonFull.setOnClickListener { toggleBag(holder.bagButton, holder.bagButtonFull) }
}

private fun getPriceString(res: Resources): String {
    return String.format(res.getString(R.string.price), "${Random.nextInt(10, 125)}.00")
}

private fun toggleBag(bagButton: ImageButton, bagButtonFull: ImageButton) {
    if (bagButton.visibility == VISIBLE) {
        bagButton.visibility = GONE
        bagButtonFull.visibility = VISIBLE
    } else {
        bagButton.visibility = VISIBLE
        bagButtonFull.visibility = GONE
    }
}

private fun toggleFav(favButton: ImageButton) {
    if (favButton.tag == NOT_FAV) {
        favButton.setImageDrawable(
            ResourcesCompat.getDrawable(
                favButton.resources,
                R.drawable.heart_bold,
                null
            )
        )
        favButton.tag = IS_FAV
    } else {
        favButton.setImageDrawable(
            ResourcesCompat.getDrawable(
                favButton.resources,
                R.drawable.heart,
                null
            )
        )
        favButton.tag = NOT_FAV
    }
}

class PopularAdapter : RecyclerView.Adapter<PortraitInventoryViewHolder>() {
    private val list =
        listOf(R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.a, R.drawable.b)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortraitInventoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_inventory_portrait, parent, false)
        return PortraitInventoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: PortraitInventoryViewHolder, position: Int) {
        setPortraitInventoryListeners(holder, list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class CollectionAdapter : RecyclerView.Adapter<CollectionViewHolder>() {
    private val list = listOf(R.drawable.winter, R.drawable.spring, R.drawable.summer)
    private val textList = listOf(R.string.winter, R.string.spring, R.string.summer)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_collection, parent, false)
        return CollectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        holder.image.setImageDrawable(
            ResourcesCompat.getDrawable(
                holder.image.resources,
                list[position],
                null
            )
        )
        holder.text.text = holder.text.context.getString(textList[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class RecommendedAdapter : RecyclerView.Adapter<PortraitInventoryViewHolder>() {
    private val list =
        listOf(R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.a, R.drawable.b)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortraitInventoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_inventory_portrait, parent, false)
        return PortraitInventoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: PortraitInventoryViewHolder, position: Int) {
        setPortraitInventoryListeners(holder, list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class PortraitInventoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val image: ImageView = itemView.findViewById(R.id.inventory_image)
    val price: TextView = itemView.findViewById(R.id.inventory_price)
    val favButton: ImageButton = itemView.findViewById(R.id.inventory_fav_btn)
    val bagButton: ImageButton = itemView.findViewById(R.id.inventory_bag_btn)
    val bagButtonFull: ImageButton = itemView.findViewById(R.id.inventory_bag_btn_full)
}

class CollectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val image: ImageView = itemView.findViewById(R.id.collection_image)
    val text: TextView = itemView.findViewById(R.id.collection_text)
}