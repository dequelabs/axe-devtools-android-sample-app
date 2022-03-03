package com.deque.mobile.axedevtoolssampleapp

import android.content.res.ColorStateList
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
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout

class FragmentCatalog : Fragment() {

    lateinit var carouselRV: RecyclerView
    lateinit var positionRv: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_catalog, null)

        val searchBar = view.findViewById<TextInputLayout>(R.id.catalog_search_box)
        val searchButton = view.findViewById<ImageButton>(R.id.catalog_search_button)
        val catalogHeading = view.findViewById<TextView>(R.id.catalog_heading)

        searchButton.setOnClickListener {
            searchBar.visibility = VISIBLE
            searchButton.visibility = GONE
            catalogHeading.visibility = GONE
        }

        searchBar.setEndIconOnClickListener {
            searchBar.visibility = GONE
            searchButton.visibility = VISIBLE
            catalogHeading.visibility = VISIBLE
        }

        val filterRv = view.findViewById<RecyclerView>(R.id.catalog_filter_rv)
        carouselRV = view.findViewById(R.id.catalog_carousel)
        positionRv = view.findViewById(R.id.catalog_carousel_pos_rv)
        val categoriesRv = view.findViewById<RecyclerView>(R.id.catalog_categories_rv)

        filterRv.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        filterRv.adapter = FilterAdapter()

        carouselRV.adapter = CarouselAdapter()
        carouselRV.layoutManager =
            CarouselLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        LinearSnapHelper().attachToRecyclerView(carouselRV)

        positionRv.adapter = CarouselPositionAdapter()
        positionRv.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        categoriesRv.layoutManager = LinearLayoutManager(activity)
        categoriesRv.adapter = CategoriesAdapter()

        return view
    }

    override fun onResume() {
        super.onResume()
        carouselScroll(carouselRV, positionRv)
    }

    override fun onPause() {
        super.onPause()
        carouselStop()
    }
}

class CategoriesAdapter : RecyclerView.Adapter<CategoriesViewHolder>() {
    private val list =
        listOf(
            CategoriesItem(R.string.t_shirts, R.string.category_catalog_desc, R.drawable.tee),
            CategoriesItem(R.string.sweatshirts, R.string.category_catalog_desc, R.drawable.hoodie),
            CategoriesItem(R.string.tank_tops, R.string.category_catalog_desc, R.drawable.tank),
            CategoriesItem(R.string.bottoms, R.string.category_catalog_desc, R.drawable.bottoms),
            CategoriesItem(R.string.accessories, R.string.category_catalog_desc, R.drawable.handbag)
        )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_catalog_collection, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val res = holder.heading.resources
        val item = list[position]
        holder.heading.text = res.getString(item.heading)
        holder.desc.text = String.format(res.getString(item.desc), res.getString(item.heading))
        holder.image.setImageDrawable(ResourcesCompat.getDrawable(res, item.image, null))
    }

    override fun getItemCount(): Int {
        return list.size
    }

}

class CategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val heading: TextView = itemView.findViewById(R.id.catalog_collection_heading)
    val desc: TextView = itemView.findViewById(R.id.catalog_collection_description)
    val image: ImageView = itemView.findViewById(R.id.catalog_collection_image)
}

class CategoriesItem(
    @StringRes val heading: Int,
    @StringRes val desc: Int,
    @DrawableRes val image: Int
)

class FilterAdapter : RecyclerView.Adapter<FilterViewHolder>() {
    private val list =
        listOf(R.string.all, R.string.male, R.string.female, R.string.boy, R.string.girl)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        return FilterViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_filter, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val res = holder.text.resources
        holder.text.text = res.getString(list[position])
        holder.text.tag = WHITE
        holder.text.setOnClickListener {
            toggleFilterColor(res, holder.text)
        }
    }

    private fun toggleFilterColor(res: Resources, textView: TextView) {
        if (textView.tag == BLACK) {
            textView.backgroundTintList = null
            textView.background =
                ResourcesCompat.getDrawable(res, R.drawable.rounded_grey_border, null)
            textView.setTextColor(res.getColor(R.color.black, null))
            textView.tag = WHITE
        } else {
            textView.backgroundTintList =
                ColorStateList.valueOf(res.getColor(R.color.black, null))
            textView.setTextColor(res.getColor(R.color.white, null))
            textView.tag = BLACK
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    companion object {
        const val WHITE = "white"
        const val BLACK = "black"
    }
}

class FilterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val text: TextView = itemView.findViewById(R.id.filter_text)
}