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
import com.example.restaurantapp.adapters.UpcomingAdapter
import com.example.restaurantapp.models.DataXXXXXXXX
import com.example.restaurantapp.models.DataXXXXXXXXX
import com.example.restaurantapp.models.RowXXXXXX
import com.example.restaurantapp.viewmodels.ReservationViewModel
import com.example.restaurantapp.viewmodels.RestaurantViewModel

class UpcomingHistory : Fragment(), UpcomingAdapter.OnItemClickListener {

    private lateinit var viewModel: ReservationViewModel
    private lateinit var r_viewModel: RestaurantViewModel

    private lateinit var localStorage: LocalStorage
    private lateinit var uid: String
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UpcomingAdapter

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
        r_viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(RestaurantViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_upcoming_history, container, false)
        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_upcoming)
        adapter = UpcomingAdapter(requireContext(), mutableListOf(), this)
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

        val bundle = Bundle()

        fragmentTransaction.replace(R.id.frame_main, newFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun getRestaurant(rid: String) {
        r_viewModel.getRestaurant(rid)
        r_viewModel.restaurant.observe(viewLifecycleOwner, Observer { data ->
            adapter.updateRestaurant(data)
            adapter.notifyDataSetChanged()
        })
    }
    override fun reservationDelete(rid:String, position:Int) {
        viewModel.reservationDelete(rid)
        adapter.deleteItem(position)


    }

}
