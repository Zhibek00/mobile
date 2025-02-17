package com.example.restaurantapp.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.example.restaurantapp.LocalStorage
import com.example.restaurantapp.R
import com.example.restaurantapp.adapters.CategoryAdapter
import com.example.restaurantapp.adapters.FilterAdapter
import com.example.restaurantapp.models.Row
import com.example.restaurantapp.viewmodels.RestaurantViewModel


class ViewAllFragment : Fragment(), FilterAdapter.OnItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FilterAdapter
    private lateinit var c_recyclerView: RecyclerView
    private lateinit var c_adapter: CategoryAdapter
    private lateinit var viewModel: RestaurantViewModel
    private lateinit var localStorage: LocalStorage
    private lateinit var uid: String
    private lateinit  var combinedList: List<String>
    var count = 0;

    var names = setOf<String>()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Initialize your localStorage here when the Fragment is attached to an activity
        localStorage = LocalStorage(requireActivity().application)
        names = localStorage.getStringSet("favorites")
        uid= localStorage.getString("uid","null")

    }


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_all, container, false)
        val search = view.findViewById<SearchView>(R.id.search)

        val receivedBoolean = arguments?.getBoolean("MY_BOOLEAN_KEY", false)
        val receivedString = arguments?.getString("category", "")
        var receivedOnceBoolean = arguments?.getBoolean("once", false)

        Log.i("gggegege", receivedString + " f " + receivedOnceBoolean)
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(RestaurantViewModel::class.java)


        recyclerView = view.findViewById(R.id.recycler_view)
        c_recyclerView = view.findViewById(R.id.category_recycle)

        recyclerView.setHasFixedSize(true)
        c_recyclerView.setHasFixedSize(true)

        viewModel.fetchCategory()

        viewModel.categoriesLiveData.observe(viewLifecycleOwner, Observer { data ->
            val c_names = data.map { it.name }.toList()
            Log.i("nn", c_names[1])
            combinedList = mutableListOf<String>().apply {
                add("All") // Add the first element of c_names
                addAll(c_names) // Add the sublist starting from index 1
            }
            c_adapter.selectedItemPosition = 0
            c_adapter.updateData(combinedList)

        })
        adapter = FilterAdapter(requireContext(), this)

        c_adapter = CategoryAdapter(requireContext()) { category ->
            var c = ""
            if (category.equals("All")) {
                c = ""
            } else {
                c = category
            }

            // Update data for RecyclerView2
            viewModel.categoryGet(c)
            viewModel.crestaurantData.observe(viewLifecycleOwner, Observer { rows ->
                adapter.updateData(rows)

            })
        }



        recyclerView.adapter = adapter
        c_recyclerView.adapter = c_adapter

        if(receivedOnceBoolean == true){
            val c = receivedString!!
            viewModel.categoriesLiveData.observe(viewLifecycleOwner, Observer { data ->
                val c_names = data.map { it.name }.toList()
                Log.i("nn", c_names[1])
                combinedList = mutableListOf<String>().apply {
                    add("All") // Add the first element of c_names
                    addAll(c_names) // Add the sublist starting from index 1
                }
                c_adapter.selectedItemPosition = combinedList.indexOf(c)
                viewModel.categoryGet(c)
                val positionToScroll =  combinedList.indexOf(c)

                c_recyclerView.layoutManager?.scrollToPosition(positionToScroll)


            })
            receivedOnceBoolean = false
            viewModel.categoryGet(c)

        }









            viewModel.fetchData()
            viewModel.favouriteList(uid)
            viewModel.favouriteData.observe(viewLifecycleOwner, Observer { rows ->
            names = rows.map { it.name }.toSet() as Set<String> })
            viewModel.restaurantData.observe(viewLifecycleOwner, Observer { rows ->
                adapter.updateData(rows)
            Log.i("rrrrr", "lklkq") })



        if(receivedBoolean == true) {
            search.requestFocus()

            Handler(Looper.getMainLooper()).postDelayed({
                val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                inputMethodManager?.toggleSoftInput(
                    InputMethodManager.SHOW_FORCED,
                    InputMethodManager.HIDE_IMPLICIT_ONLY
                )
            }, 140)
        }
            // Set the SearchView query listener
           search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    // Handle search query submission
                    return false
                }
               override fun onQueryTextChange(newText: String?): Boolean {
                   // Handle search query text change
                   // Perform filtering based on the search query
                   adapter.filter.filter(newText)
                   return true
               }
            })

        return view
    }

    override fun onItemClick(data: Row) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        // Pass data to the new fragment
        val newFragment = RestaurantDetailedFragment()

        val bundle = Bundle()
        // Put your object into the bundle
        bundle.putParcelable("data_key", data)
        newFragment.arguments = bundle

        fragmentTransaction.replace(R.id.frame_main, newFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
    override fun fav(rid: String){
        viewModel.favouriteAdd(rid, uid)
    }

    override fun favDelete(rid: String){
        viewModel.favouriteDelete(rid, uid)
    }



}