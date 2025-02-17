package com.example.restaurantapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.R

class TimeAdapter(private val context: Context, private var dataList: List<String>) :
    RecyclerView.Adapter<TimeAdapter.MyViewHolder>() {


    var selectedItemPosition: Int = RecyclerView.NO_POSITION


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_time, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(dataList[position])


        if (position == selectedItemPosition) {
            holder.itemView.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.blue))
            holder.textView.setTextColor(ContextCompat.getColor(context, R.color.white))
        } else {
            holder.itemView.backgroundTintList = null
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
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun updateData(newRows: List<String>) {
        dataList = newRows
        notifyDataSetChanged()
    }



    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.time)

        @SuppressLint("ResourceAsColor")
        fun bind(data: String) {
            textView.text = data

            Log.i("kk", data)
        }
    }
}
