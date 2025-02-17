package com.example.restaurantapp.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.LocalStorage
import com.example.restaurantapp.R
import com.example.restaurantapp.adapters.PreviousAdapter
import com.example.restaurantapp.models.RowXXXXXX
import com.example.restaurantapp.viewmodels.ReservationViewModel
import com.example.restaurantapp.viewmodels.RestaurantViewModel


class PreviousHistory : Fragment(), PreviousAdapter.OnItemClickListener {

    private lateinit var viewModel: ReservationViewModel
    private lateinit var r_viewModel: RestaurantViewModel

    private lateinit var localStorage: LocalStorage
    private lateinit var uid: String
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PreviousAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        localStorage = LocalStorage(requireActivity().application)
        uid = localStorage.getString("uid", "null")
        Log.i("g", uid)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(ReservationViewModel::class.java)
        r_viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(
            RestaurantViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_upcoming_history, container, false)
        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_upcoming)
        adapter = PreviousAdapter(requireContext(), mutableListOf(), this)
        recyclerView.adapter = adapter
        viewModel.reservationGet(uid)
        viewModel.reservationData.observe(viewLifecycleOwner, Observer{ data->
            adapter.updateData(data as MutableList<RowXXXXXX>)
            for (item in data) {
                getRestaurant(item.rid!!)
            }
        })
        return view
    }

    override fun onItemClick(data: RowXXXXXX) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val newFragment = ReservationFinalFragment()
        val newFragment1 = OverviewFragment()

        val bundle = Bundle()


        fragmentTransaction.replace(R.id.frame_main, newFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun fav(rid: String){
        r_viewModel.favouriteAdd(rid, uid)
    }

    override fun favDelete(rid: String){
        r_viewModel.favouriteDelete(rid, uid)
    }
    @SuppressLint("NotifyDataSetChanged")
    override fun getRestaurant(rid: String) {
        r_viewModel.getRestaurant(rid)
        r_viewModel.restaurant.observe(viewLifecycleOwner, Observer { data ->
            adapter.updateRestaurant(data)
            adapter.notifyDataSetChanged()
        })
    }
}
