package com.example.restaurantapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.R
import com.example.restaurantapp.fragments.UpcomingHistory
import com.example.restaurantapp.models.DataXXXXXXXX
import com.example.restaurantapp.models.DataXXXXXXXXX
import com.example.restaurantapp.models.RowXXXXXX
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class UpcomingAdapter(
    private val context: Context,
    private var dataList: MutableList<RowXXXXXX>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<UpcomingAdapter.MyViewHolder>() {

    private var restaurant: DataXXXXXXXXX? = null

    interface OnItemClickListener {
        fun onItemClick(data: RowXXXXXX)

        fun getRestaurant(rid: String)
        fun reservationDelete(rid:String, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_restaurant_upcoming, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = dataList[position]
        Log.i("ppp",currentItem.reservationStartTime.toString() )
        val a = currentItem.reservationStartTime
        Log.i("aaa",  (a >= System.currentTimeMillis()/1000).toString())
            holder.workTime.text = timeConverter(currentItem.reservationStartTime)
            holder.guest.text = currentItem.tableType.toString()
            holder.name.text = restaurant?.name ?: ""
            holder.address.text = restaurant?.address ?: ""
       // Picasso.get().load("http://172.20.10.9:2002/" + restaurant?.path!!).into(holder.image)

        holder.cancel.setOnClickListener{
                itemClickListener.reservationDelete(currentItem.reservationId!!, position)
            }
            holder.itemView.setOnClickListener {
                itemClickListener.onItemClick(currentItem)
            }


    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("SimpleDateFormat")
    private fun timeConverter(milliseconds: Int): String {
        val timestampSeconds = milliseconds.toLong()
        val date = Date(timestampSeconds * 1000) // Convert seconds to milliseconds
        val sdf = SimpleDateFormat("EEEE, d MMMM yyyy HH:mm", Locale.getDefault())
        return sdf.format(date)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val guest: TextView = itemView.findViewById(R.id.guest)
        val address: TextView = itemView.findViewById(R.id.address)
        val workTime: TextView = itemView.findViewById(R.id.time)
        val cancel: AppCompatButton = itemView.findViewById(R.id.cancel_btn)
        val image: ImageView = itemView.findViewById(R.id.image)


    }

    fun updateRestaurant(restaurant: DataXXXXXXXXX?) {
        this.restaurant = restaurant
        notifyDataSetChanged()
    }

    fun updateData(newRows: MutableList<RowXXXXXX>) {
        dataList = newRows.filter { it.reservationStartTime >= System.currentTimeMillis() / 1000 }.toMutableList()
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        if (position in 0  until dataList.size) {
            dataList.removeAt(position)
            updateData(dataList)

        }


    }
}
