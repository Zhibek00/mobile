package com.example.restaurantapp.fragments
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.LocalStorage
import com.example.restaurantapp.R
import com.example.restaurantapp.adapters.FavouriteAdapter
import com.example.restaurantapp.models.Row
import com.example.restaurantapp.viewmodels.RestaurantViewModel
import kotlin.properties.Delegates

class LikedFragment : Fragment(), FavouriteAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavouriteAdapter
    private lateinit var viewModel: RestaurantViewModel
    lateinit var localStorage: LocalStorage
    private lateinit var uid: String
    private var position by Delegates.notNull<Int>()
    private lateinit var rootView: View
    private lateinit var emptyView: View

    override fun onAttach(context: Context) {
        super.onAttach(context)
        localStorage = LocalStorage(requireActivity().application)
        uid = localStorage.getString("uid", "null")
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_liked, container, false)
        recyclerView = rootView.findViewById(R.id.recycle_fav)
        recyclerView.setHasFixedSize(true)
        adapter = FavouriteAdapter(requireContext(), mutableListOf(), this)
        recyclerView.adapter = adapter


        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(RestaurantViewModel::class.java)
        viewModel.favouriteList(uid)
        viewModel.favouriteData.observe(viewLifecycleOwner, Observer { rows ->
                adapter.updateData(rows.toMutableList())
        })

        return rootView
    }

    private fun showEmptyView() {
        recyclerView.visibility = View.GONE
        emptyView.visibility = View.VISIBLE
    }

    private fun showRecyclerView() {
        recyclerView.visibility = View.VISIBLE
        emptyView.visibility = View.GONE
        recyclerView.adapter = adapter
    }

    override fun onItemClick(data: Row) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val newFragment = RestaurantDetailedFragment()
        val bundle = Bundle()
        bundle.putParcelable("data_key", data)
        newFragment.arguments = bundle

        fragmentTransaction.replace(R.id.frame_main, newFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun fav(rid: String) {
        viewModel.favouriteAdd(rid, uid)
    }

    override fun favDelete(rid: String, position: Int) {
        this.position = position
        viewModel.favouriteDelete(rid, uid)
    }
}
