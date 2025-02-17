package com.example.restaurantapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.R
import com.example.restaurantapp.adapters.MenuAdapter
import com.example.restaurantapp.adapters.RestaurantAdapter
import com.example.restaurantapp.models.Filter
import com.example.restaurantapp.models.Row
import com.example.restaurantapp.models.RowXX
import com.example.restaurantapp.viewmodels.DetailedRestaurantViewModel
import com.example.restaurantapp.viewmodels.ReservationViewModel
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*


class OverviewFragment : Fragment() {
    private lateinit var viewModel: DetailedRestaurantViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MenuAdapter


    @SuppressLint("MissingInflatedId", "SetTextI18n", "StringFormatMatches")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(DetailedRestaurantViewModel::class.java)

        val receivedData: Row? = arguments?.getParcelable("data_key")
        val view = inflater.inflate(R.layout.fragment_overview, container, false)




      
        return view
    }
    private fun timeConverter(milliseconds: Int): String {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliseconds.toLong()*1000
        val formattedTime = dateFormat.format(calendar.time)
        return formattedTime
    }


}


