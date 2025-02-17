package com.example.restaurantapp.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Half.toFloat
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.LocalStorage
import com.example.restaurantapp.R
import com.example.restaurantapp.adapters.CommentAdapter
import com.example.restaurantapp.adapters.RestaurantAdapter
import com.example.restaurantapp.models.Row
import com.example.restaurantapp.viewmodels.DetailedRestaurantViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class ReviewFragment : Fragment() {
    private lateinit var viewModel: DetailedRestaurantViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CommentAdapter
    private lateinit var localStorage: LocalStorage
    private lateinit var uid: String


    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Initialize your localStorage here when the Fragment is attached to an activity
        localStorage = LocalStorage(requireActivity().application)
        uid= localStorage.getString("uid","null")
        Log.i("g", uid)
    }


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(DetailedRestaurantViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_review, container, false)


        recyclerView = view.findViewById(R.id.recycle_comment)
        recyclerView.setHasFixedSize(true)
        adapter = CommentAdapter(requireContext(), emptyList())
        recyclerView.adapter = adapter
        val receivedData: Row? = arguments?.getParcelable("data_key")
        val rating = view.findViewById<TextView>(R.id.rating)



        viewModel.getReviews(receivedData!!.rid!!)
        viewModel.reviewData.observe(viewLifecycleOwner, Observer { rows ->
            if(rows.isNotEmpty()) {
                rating.text = rows.get(0).avgRate.totalRate.toString().subSequence(0, 3)
                Log.i("llll", rows.toString())

                adapter.updateData(rows.get(0).reviews)
            }

        })






        return view
    }



}