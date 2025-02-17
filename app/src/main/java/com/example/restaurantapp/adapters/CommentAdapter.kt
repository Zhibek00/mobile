package com.example.restaurantapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.R
import com.example.restaurantapp.models.ReviewXXX

class CommentAdapter (private val context: Context, private var dataList: List<ReviewXXX>) : RecyclerView.Adapter<CommentAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_comment, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // Bind data to views in ViewHolder
        val currentItem = dataList[position]
        holder.name.text = currentItem.name
        holder.comment.text = currentItem.comment
        holder.rating.rating = currentItem.rating.toFloat()
        holder.rating.setIsIndicator(true)


    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newRows:List<ReviewXXX>) {
        dataList = newRows
        notifyDataSetChanged()
    }



    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Define views here using itemView.findViewById()
        val name: TextView = itemView.findViewById(R.id.name)
        val comment: TextView = itemView.findViewById(R.id.comment)
        val rating: RatingBar = itemView.findViewById(R.id.ratingBar2)


    }

    override fun getItemCount(): Int {
        return dataList.size
    }


}