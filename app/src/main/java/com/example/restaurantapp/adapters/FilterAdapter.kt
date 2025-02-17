package com.example.restaurantapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.R
import com.example.restaurantapp.fragments.ViewAllFragment
import com.example.restaurantapp.models.Row
import com.facebook.shimmer.ShimmerFrameLayout
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
class FilterAdapter(
    private val context: Context,
    private val itemClickListener: ViewAllFragment
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {
    private var dataList: List<Row> = emptyList()
    private var filteredList: List<Row> = emptyList()

    interface OnItemClickListener {
        fun onItemClick(data: Row)
        fun fav(rid: String)
        fun favDelete(rid: String)
    }

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_SHIMMER = 1

    private var isShimmer = true

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val image: ImageView = itemView.findViewById(R.id.image)
        val rating: TextView = itemView.findViewById(R.id.rating)
        val address: TextView = itemView.findViewById(R.id.address)
        val workTime: TextView = itemView.findViewById(R.id.work_time)
        val heart: FrameLayout = itemView.findViewById(R.id.favourite_btn)
        val heart_fill: ImageView = itemView.findViewById(R.id.heart)
    }

    inner class ShimmerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(context).inflate(R.layout.card_restaurant, parent, false)
            ItemViewHolder(view)
        } else {
            val view = LayoutInflater.from(context).inflate(R.layout.shimmer_card_restaurant, parent, false)
            ShimmerViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return if (isShimmer) 10 else dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (isShimmer) VIEW_TYPE_SHIMMER else VIEW_TYPE_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            val currentItem = dataList[position]
            holder.name.text = currentItem.name
            holder.address.text = currentItem.address
            holder.workTime.text = "${timeConverter(currentItem.workstarttime)} - ${timeConverter(currentItem.workendtime)}"
            Picasso.get().load("http://172.20.10.9:2002/" +currentItem.path).into(holder.image)
            if(currentItem.name in itemClickListener.names){
                holder.heart_fill.setImageResource(R.drawable.heart_filled)
                currentItem.isFavorite = 1
            }
            else{
                holder.heart_fill.setImageResource(R.drawable.heart)
            }
            holder.heart.setOnClickListener{
                if(currentItem.isFavorite == 0){
                    itemClickListener.fav(currentItem.rid!!)
                    holder.heart_fill.setImageResource(R.drawable.heart_filled)
                    currentItem.isFavorite = 1
                }
                else if(currentItem.isFavorite == 1){
                    holder.heart_fill.setImageResource(R.drawable.heart)
                    currentItem.isFavorite = 0
                    itemClickListener.favDelete(currentItem.rid!!)
                }
            }
            holder.itemView.setOnClickListener {
                itemClickListener.onItemClick(currentItem)
            }
        } else if (holder is ShimmerViewHolder) {
            // Handle shimmer layout
            val shimmerLayout = holder.itemView.findViewById<ShimmerFrameLayout>(R.id.shim)
            shimmerLayout.startShimmer()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newRows: List<Row>) {
        dataList = newRows
        isShimmer = false
        Log.d("ee de", "publishResults called with constraint: $dataList")
        notifyDataSetChanged()
    }

    @SuppressLint("SimpleDateFormat")
    private fun timeConverter(milliseconds: Int): String {
        val timestampSeconds = milliseconds.toLong()
        val date = Date(timestampSeconds * 1000) // Convert seconds to milliseconds
        val sdf = SimpleDateFormat("HH:mm") // Format to get only hours and minutes
        return sdf.format(date)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                val searchString = constraint.toString().toLowerCase(Locale.getDefault()).trim()

                if (searchString.isEmpty()) {
                    filteredList = dataList // Show original dataList if search query is empty
                } else {
                    // Filter the dataList based on the name field
                    filteredList = dataList.filter { row ->
                        row.name?.toLowerCase(Locale.getDefault())?.contains(searchString) == true
                    }
                }

                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results?.values is List<*>) {
                    @Suppress("UNCHECKED_CAST")
                    filteredList = results.values as List<Row>
                    notifyDataSetChanged()
                }
            }
        }
    }

   /* override fun getFilter(): Filter {
        Log.d("ee de 11", "publishResults called with constraint: $dataList")

        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                Log.d("FilterAdapter", "performFiltering called with constraint: $constraint")
                val filterResults = FilterResults()
                if (constraint.isNullOrEmpty()) {
                    filterResults.values = dataList

                } else if (constraint.length == 1) {
                    filterResults.values = emptyList<Row>()
                } else {
                    val filtered = dataList.filter { item ->
                        item.name?.toLowerCase(Locale.getDefault())?.contains(constraint.toString().toLowerCase(Locale.getDefault())) ?: false

                    }
                    filterResults.values = filtered
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                Log.d("FilterAdapter", "publishResults called with constraint: $constraint")
                if (results?.values is List<*>) {
                    @Suppress("UNCHECKED_CAST")
                    dataList = results.values as List<Row>
                    Log.d("FilterAdapter", "publishResults called with constraint: $dataList")

                    notifyDataSetChanged()
                }
            }
        }
    }*/
}
