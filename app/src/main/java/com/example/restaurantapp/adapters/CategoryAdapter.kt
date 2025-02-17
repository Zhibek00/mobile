package com.example.restaurantapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.R
import com.example.restaurantapp.models.Row
import com.facebook.shimmer.ShimmerFrameLayout

class CategoryAdapter(private val context: Context, private val onItemClick: (String) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var dataList: List<String> = emptyList()
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_SHIMMER = 1
    private var isShimmer = true
    var selectedItemPosition: Int = RecyclerView.NO_POSITION

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.text1)
        @SuppressLint("ResourceAsColor")
        fun bind(data: String) {
            textView.text = data

            Log.i("kk", data)
        }
    }

    inner class ShimmerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(context).inflate(R.layout.card_filter, parent, false)
            ItemViewHolder(view)
        } else {
            val view = LayoutInflater.from(context).inflate(R.layout.shimmer_card_filter, parent, false)
            ShimmerViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.bind(dataList[position])

            // Check if the current item is selected and apply colors accordingly
            if (position == selectedItemPosition) {
                holder.itemView.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.purple))
                holder.textView.setTextColor(ContextCompat.getColor(context, R.color.white))
            } else {
                holder.itemView.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.grey1))
                holder.textView.setTextColor(ContextCompat.getColor(context, R.color.black))
            }

            holder.itemView.setOnClickListener {
                // Update selected item position
                val previousSelectedPosition = selectedItemPosition
                selectedItemPosition = holder.adapterPosition

                // Notify adapter about the changes
                notifyItemChanged(previousSelectedPosition)
                notifyItemChanged(selectedItemPosition)

                // Invoke onItemClick callback
                onItemClick(dataList[position])
            }
        } else if (holder is ShimmerViewHolder) {
            // Handle shimmer layout
            val shimmerLayout = holder.itemView.findViewById<ShimmerFrameLayout>(R.id.shim)
            shimmerLayout.startShimmer()
        }
    }

    override fun getItemCount(): Int {
        return if (isShimmer) 5 else dataList.size // Change the number of shimmer items as per your requirement
    }

    override fun getItemViewType(position: Int): Int {
        return if (isShimmer) VIEW_TYPE_SHIMMER else VIEW_TYPE_ITEM
    }

    fun updateData(newDataList: List<String>) {
        dataList = newDataList
        isShimmer = false
        notifyDataSetChanged()
    }

    fun updateColor(position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            // Check if the position is valid
            selectedItemPosition = position // Update the selected item position
            notifyDataSetChanged() // Notify the adapter to rebind all items
        }
    }
}
