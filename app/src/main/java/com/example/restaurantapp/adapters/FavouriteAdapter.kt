package com.example.restaurantapp.adapters


import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.restaurantapp.R
import com.example.restaurantapp.fragments.LikedFragment
import com.example.restaurantapp.models.Row
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class FavouriteAdapter(private val context: Context, private var dataList: MutableList<Row>, private val itemClickListener: LikedFragment) : RecyclerView.Adapter<FavouriteAdapter.MyViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(data: Row)
        fun fav(rid: String)
        fun favDelete(rid: String, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_restaurant, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.name.text = currentItem.name
        holder.address.text = currentItem.address
        holder.workTime.text = timeConverter(currentItem.workstarttime) + " - " + timeConverter(currentItem.workendtime)
        Picasso.get().load(R.string.api.toString() + currentItem.path).into(holder.image)

        if (currentItem.isFavorite == 1) {
            holder.heart_fill.setImageResource(R.drawable.heart_filled)
        }

        holder.heart.setOnClickListener {
            if (currentItem.isFavorite == 1) {
                holder.heart_fill.setImageResource(R.drawable.heart)
                currentItem.isFavorite = 0
                itemClickListener.favDelete(currentItem.rid!!, position)
                deleteItem(position)
            }
        }

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    private fun timeConverter(milliseconds: Int): String {
        val timestampSeconds = milliseconds.toLong()
        val date = Date(timestampSeconds * 1000)
        val sdf = SimpleDateFormat("HH:mm")
        return sdf.format(date)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val image: ImageView = itemView.findViewById(R.id.image)
        val address: TextView = itemView.findViewById(R.id.address)
        val workTime: TextView = itemView.findViewById(R.id.work_time)
        val heart: FrameLayout = itemView.findViewById(R.id.favourite_btn)
        val heart_fill: ImageView = itemView.findViewById(R.id.heart)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newRows: MutableList<Row>) {
        dataList = newRows
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteItem(position: Int) {
        if (position in 0 until dataList.size) {
            dataList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, dataList.size)
            notifyDataSetChanged()
            val names = dataList.map { it.name }?.toSet() as Set<String>
            itemClickListener.localStorage.saveStringSet("favorites", names)

        }
    }


}

