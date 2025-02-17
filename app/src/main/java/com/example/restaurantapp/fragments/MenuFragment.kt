package com.example.restaurantapp.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.R
import com.example.restaurantapp.adapters.CategoryAdapter
import com.example.restaurantapp.adapters.MenuAdapter
import com.example.restaurantapp.adapters.MenuAdapter1
import com.example.restaurantapp.adapters.RestaurantAdapter
import com.example.restaurantapp.models.Filter
import com.example.restaurantapp.models.Row
import com.example.restaurantapp.viewmodels.DetailedRestaurantViewModel


class MenuFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var c_recyclerView: RecyclerView
    private lateinit var adapter: MenuAdapter1
    private lateinit var c_adapter: CategoryAdapter
    lateinit var filter: Filter
    private lateinit var viewModel: DetailedRestaurantViewModel
    var nameFirst: String = ""
    private lateinit  var combinedList: List<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        val receivedData: Row? = arguments?.getParcelable("data_key")
        recyclerView = view.findViewById(R.id.recycler_menu)
        c_recyclerView = view.findViewById(R.id.recycler_category)
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(DetailedRestaurantViewModel::class.java)
        recyclerView.setHasFixedSize(true)
        c_recyclerView.setHasFixedSize(true)

        adapter = MenuAdapter1(requireContext(), emptyList())
        c_adapter = CategoryAdapter(requireContext()){ category ->
            var c = ""
            if(category == "All"){

            }
            else{
                c =category
            }
            // Update data for RecyclerView2
            viewModel.getMenu(receivedData?.rid!!, Filter(c,""))
            viewModel.menuData.observe(viewLifecycleOwner, Observer{rows ->


                adapter.updateData(rows)
            })

        }

        recyclerView.adapter = adapter
        c_recyclerView.adapter = c_adapter
        viewModel.getMenuCategory()
        viewModel.category.observe(viewLifecycleOwner, Observer{ data ->
            combinedList = mutableListOf<String>().apply {
                add("All") // Add the first element of c_names
                addAll(data) // Add the sublist starting from index 1
            }
            c_adapter.updateData(combinedList)
            c_adapter.selectedItemPosition = 0
            nameFirst = ""
            viewModel.getMenu(receivedData?.rid!!, Filter(nameFirst,""))
            viewModel.menuData.observe(viewLifecycleOwner, Observer{rows ->

                adapter.updateData(rows)
            })

        })












        return view
    }

}