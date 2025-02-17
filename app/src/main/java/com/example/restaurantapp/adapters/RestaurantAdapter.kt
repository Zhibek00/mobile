package com.example.restaurantapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.R
import com.example.restaurantapp.fragments.HomeFragment
import com.example.restaurantapp.models.Row
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class RestaurantAdapter(
    private val context: Context,
    private val itemClickListener: HomeFragment
) : RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {

    private var dataList = emptyList<Row>()


    init {
        // Enable stable IDs
        setHasStableIds(true)
    }


    interface OnItemClickListener {
        fun onItemClick(data: Row)
        fun fav(rid: String)
        fun favDelete(rid: String)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val image: ImageView = itemView.findViewById(R.id.image)
        val rating: TextView = itemView.findViewById(R.id.rating)
        val address: TextView = itemView.findViewById(R.id.address)
        val workTime: TextView = itemView.findViewById(R.id.work_time)
        val heart: FrameLayout = itemView.findViewById(R.id.favourite_btn)
        val heart_fill: ImageView = itemView.findViewById(R.id.heart_e)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_restaurant_small, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemId(position: Int): Long {
        val product: Row = dataList[position]
        Log.i("iii", product.rid.hashCode().toLong().toString()+" "+product.name)
        return product.name.hashCode().toLong()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = dataList[position]

        holder.name.text = currentItem.name
        holder.address.text = currentItem.address
        holder.workTime.text =
            timeConverter(currentItem.workstarttime) + " - " + timeConverter(currentItem.workendtime)
        Picasso.get().load("http://172.20.10.9:2002/" + currentItem.path )
            .into(holder.image)
        holder.rating.text = currentItem.rating.toString()
        if (currentItem.name in itemClickListener.names) {
            holder.heart_fill.setImageResource(R.drawable.heart_filled)
            currentItem.isFavorite = 1
        } else {
            holder.heart_fill.setImageResource(R.drawable.heart)
        }

        holder.heart.setOnClickListener {
            if (currentItem.isFavorite == 0) {
                itemClickListener.fav(currentItem.rid!!)
                holder.heart_fill.setImageResource(R.drawable.heart_filled)
                currentItem.isFavorite = 1
            } else if (currentItem.isFavorite == 1) {
                holder.heart_fill.setImageResource(R.drawable.heart)
                currentItem.isFavorite = 0
                itemClickListener.favDelete(currentItem.rid!!)
            }
        }

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(currentItem)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun timeConverter(milliseconds: Int): String {
        val timestampSeconds = milliseconds.toLong()
        val date = Date(timestampSeconds * 1000)
        val sdf = SimpleDateFormat("HH:mm")
        return sdf.format(date)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newRows: List<Row>) {

        dataList = newRows
        notifyDataSetChanged()
    }


}
