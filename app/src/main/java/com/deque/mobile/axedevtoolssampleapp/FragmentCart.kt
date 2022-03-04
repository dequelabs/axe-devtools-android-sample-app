package com.deque.mobile.axedevtoolssampleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlin.random.Random

class FragmentCart : Fragment() {
    lateinit var cartRv: RecyclerView
    lateinit var totalItems: TextView
    lateinit var totalPrice: TextView

    private val updateTotalsCallback: (hasItems: Boolean) -> Unit = { hasItems ->
        updateTotals(hasItems)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cart, null)

        val cartAdapter = CartAdapter(updateTotalsCallback)

        cartRv = view.findViewById(R.id.cart_rv)
        cartRv.layoutManager = LinearLayoutManager(activity)
        cartRv.adapter = cartAdapter
        cartRv.viewTreeObserver.addOnGlobalLayoutListener {
            updateTotals(true)
        }

        val deleteAll = view.findViewById<TextView>(R.id.cart_delete_all)
        deleteAll.setOnClickListener {
            (cartRv.adapter as CartAdapter).clear()
            it.visibility = GONE
        }

        totalItems = view.findViewById(R.id.cart_total_items)
        totalPrice = view.findViewById(R.id.cart_total_price)

        return view
    }

    private fun updateTotals(hasItems: Boolean) {
        val totalItemsString = getString(R.string.cart_total_items)
        val totalPriceString = getString(R.string.cart_total_price)
        val cartItemView1 =
            (cartRv.layoutManager as LinearLayoutManager).findViewByPosition(0)
        val cartItemView2 =
            (cartRv.layoutManager as LinearLayoutManager).findViewByPosition(1)


        if (hasItems && cartItemView1 != null && cartItemView2 != null) {

            val count1 = cartItemView1.findViewById<TextView>(R.id.cart_item_counter)
            val count2 = cartItemView2.findViewById<TextView>(R.id.cart_item_counter)

            val price1 = cartItemView1.findViewById<TextView>(R.id.cart_item_price)
            val price2 = cartItemView2.findViewById<TextView>(R.id.cart_item_price)

            totalItems.text =
                String.format(
                    totalItemsString,
                    getInt(count1) + getInt(count2)
                )

            totalPrice.text = String.format(
                totalPriceString,
                (getInt(count1) * getInt(price1) + getInt(count2) * getInt(price2))
            )
        } else {
            totalItems.text = String.format(totalItemsString, "0")
            totalPrice.text = String.format(totalPriceString, "0")
        }
    }

    private fun getInt(textView: TextView): Int {
        val regex = Regex("[^A-Za-z0-9]")
        var string = (textView.text as String)
        if (string.contains('.')) {
            string = string.subSequence(0, string.length - 2).toString()
        }
        val result = regex.replace(string, "")

        return result.toInt()
    }
}

class CartAdapter(val totalsCallback: (hasItems: Boolean) -> Unit) :
    RecyclerView.Adapter<CartViewHolder>() {
    private val list = listOf(R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d)

    private var numItems = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val res = holder.image.resources
        val randomNum = Random.nextInt(3)
        holder.image.setImageDrawable(ResourcesCompat.getDrawable(res, list[randomNum], null))
        holder.title.text = res.getString(R.string.explorinc_life)
        holder.color.text = res.getString(R.string.gray)
        holder.price.text =
            String.format(res.getString(R.string.price), "${Random.nextInt(10, 150)}.00")
        holder.counter.text = "${holder.count}"

        holder.increment.setOnClickListener {
            if (holder.count < 10) {
                holder.count++
                holder.counter.text = "${holder.count}"
            }
            totalsCallback(true)
        }
        holder.decrement.setOnClickListener {
            if (holder.count > 1) {
                holder.count--
                holder.counter.text = "${holder.count}"
            }
            totalsCallback(true)
        }
    }

    override fun getItemCount(): Int {
        return numItems
    }

    fun clear() {
        numItems = 0
        totalsCallback(false)
        notifyDataSetChanged()
    }

}

class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var count: Int = Random.nextInt(1, 3)
    val title: TextView = itemView.findViewById(R.id.cart_item_title)
    val color: TextView = itemView.findViewById(R.id.cart_item_color)
    val price: TextView = itemView.findViewById(R.id.cart_item_price)
    val counter: TextView = itemView.findViewById(R.id.cart_item_counter)

    val decrement: Button = itemView.findViewById(R.id.cart_item_decrement)
    val increment: Button = itemView.findViewById(R.id.cart_item_increment)

    val image: ImageView = itemView.findViewById(R.id.cart_item_image)
}
