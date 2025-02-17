package com.example.restaurantapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.restaurantapp.R
import com.example.restaurantapp.models.ReservationRequest
import com.example.restaurantapp.models.Row
import com.example.restaurantapp.viewmodels.ReservationViewModel
import java.text.SimpleDateFormat
import java.util.*


class ReservationFinalFragment : Fragment() {

    private lateinit var viewModel: ReservationViewModel

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_reservation_final, container, false)
        val name = view.findViewById<TextView>(R.id.name_t)
        val address = view.findViewById<TextView>(R.id.address)
        val time = view.findViewById<TextView>(R.id.time)
        val guest = view.findViewById<TextView>(R.id.guest)
        val date = view.findViewById<TextView>(R.id.date)

        val occasion = view.findViewById<TextView>(R.id.occasion)

        val receivedData1: ReservationRequest? = arguments?.getParcelable("data_key1")
        val receivedData: Row? = arguments?.getParcelable("data_key")
        val receivedData0: String? = arguments?.getString("data")

        if(receivedData!= null&& receivedData0!=null&&receivedData1!=null) {
            name.text = receivedData!!.name
            address.text = receivedData!!.address
            time.text = timeConverter(receivedData1!!.reservationStartTime)
            date.text = timeConverter1(receivedData1!!.reservationStartTime)
            occasion.text = receivedData1.occasion
            guest.text = receivedData0



            Log.i("reserve", receivedData.toString())
            viewModel = ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
            ).get(
                ReservationViewModel::class.java
            )
            viewModel.reservationAdd(receivedData1!!)
        }
        return view
    }

    private fun timeConverter(milliseconds: Int): String {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliseconds.toLong()*1000
        val formattedTime = dateFormat.format(calendar.time)
        return formattedTime
    }

    private fun timeConverter1(milliseconds: Int): String {
        val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliseconds.toLong()*1000
        val formattedTime = dateFormat.format(calendar.time)
        return formattedTime
    }
}