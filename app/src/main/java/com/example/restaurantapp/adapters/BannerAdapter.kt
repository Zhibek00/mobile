package com.example.restaurantapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.R
import com.example.restaurantapp.models.RowXXXX
import com.facebook.shimmer.ShimmerFrameLayout
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class BannerAdapter(private val context: Context, private var dataList: List<RowXXXX>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_SHIMMER = 1
    private var isShimmer = true

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Define views here using itemView.findViewById()
        val img: ImageView = itemView.findViewById(R.id.image)
    }

    inner class ShimmerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(context).inflate(R.layout.card_restaurant1, parent, false)
            ItemViewHolder(view)
        } else {
            val view = LayoutInflater.from(context).inflate(R.layout.shimmer_card_restaurant1, parent, false)
            ShimmerViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return if (isShimmer) 5 else dataList.size // Change the number of shimmer items as per your requirement
    }

    override fun getItemViewType(position: Int): Int {
        return if (isShimmer) VIEW_TYPE_SHIMMER else VIEW_TYPE_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            val currentItem = dataList[position]
            Picasso.get()
                .load("http://172.20.10.9:2002/" + currentItem.path)
                .into(holder.img, object : Callback {
                    override fun onSuccess() {
                        // Image loaded successfully
                    }

                    override fun onError(e: Exception?) {
                        // Handle error
                        e?.printStackTrace()
                    }
                })
        } else if (holder is ShimmerViewHolder) {
            // Handle shimmer layout
            val shimmerLayout = holder.itemView.findViewById<ShimmerFrameLayout>(R.id.shim)
            shimmerLayout.startShimmer()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newRows: List<RowXXXX>) {
        dataList = newRows
        isShimmer = false

        notifyDataSetChanged()
    }
}
