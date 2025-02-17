package com.example.restaurantapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.R
import com.example.restaurantapp.models.RowXX
import com.squareup.picasso.Picasso


class MenuAdapter1 (private val context: Context, private var dataList: List<RowXX>) : RecyclerView.Adapter<MenuAdapter1.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_menu, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // Bind data to views in ViewHolder
        val currentItem = dataList[position]
        holder.name.text = currentItem.name
        holder.receipt.text = currentItem.ingredient
        holder.price.text = currentItem.price.toString() +" tg"
        Picasso.get().load("http://172.20.10.9:2002/" + currentItem.path).into(holder.image)



    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newRows:List<RowXX>) {
        dataList = newRows
        notifyDataSetChanged()
    }



    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Define views here using itemView.findViewById()
        val name: TextView = itemView.findViewById(R.id.name)
        val image: ImageView = itemView.findViewById(R.id.image)
        val receipt: TextView = itemView.findViewById(R.id.receipt)
        val price: TextView = itemView.findViewById(R.id.price)


    }

    override fun getItemCount(): Int {
        return dataList.size
    }


}