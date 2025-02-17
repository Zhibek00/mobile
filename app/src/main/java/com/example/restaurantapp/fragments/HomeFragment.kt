package com.example.restaurantapp.fragments
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.transition.TransitionInflater
import com.example.restaurantapp.LocalStorage
import com.example.restaurantapp.R
import com.example.restaurantapp.adapters.BannerAdapter
import com.example.restaurantapp.adapters.CategoryAdapter
import com.example.restaurantapp.adapters.RestaurantAdapter
import com.example.restaurantapp.models.Row
import com.example.restaurantapp.viewmodels.RestaurantViewModel


class HomeFragment : Fragment(), RestaurantAdapter.OnItemClickListener{
    private lateinit var recyclerView: RecyclerView
    private lateinit var b_recyclerView: RecyclerView

    private lateinit var c_recyclerView: RecyclerView
    private lateinit var c_adapter: CategoryAdapter

    private lateinit var adapter: RestaurantAdapter
    private lateinit var b_adapter: BannerAdapter

    private lateinit var viewModel: RestaurantViewModel
    private lateinit var localStorage: LocalStorage
    private lateinit var uid: String
    var names = setOf<String>()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        localStorage = LocalStorage(requireActivity().application)
        uid = localStorage.getString("uid","null")
        Log.i("g", uid)
        names = localStorage.getStringSet("favorites")
    }

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.restaurant_all_recycle)
        c_recyclerView = view.findViewById(R.id.category_recycle)
        b_recyclerView = view.findViewById(R.id.recycler_banner)

        val horizontalLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = horizontalLayoutManager

        c_recyclerView.setHasFixedSize(true)
        b_recyclerView.setHasFixedSize(true)
        recyclerView.setHasFixedSize(true)

        b_adapter = BannerAdapter(requireContext(), emptyList())
        adapter = RestaurantAdapter(requireContext(), this)
        c_adapter = CategoryAdapter(requireContext()){ category ->
            val fragmentB = ViewAllFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(
                R.anim.slide_in, // enter
                R.anim.fade_out, // exit
                R.anim.fade_in, // popEnter
                R.anim.slide_out // popExit
            )
            transaction.replace(
                R.id.frame_main,
                fragmentB
            )
            val bundle = Bundle().apply {
                putString("category", category)
                putBoolean("once", true)
            }
            fragmentB.arguments = bundle
            transaction.addToBackStack(null)
            transaction.commit()

        }

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(RestaurantViewModel::class.java)
        viewModel.fetchData()
        viewModel.fetchCategory()

        viewModel.favouriteList(uid)
        viewModel.favouriteData.observe(viewLifecycleOwner, Observer { rows ->
            names = rows.map { it.name }.toSet() as Set<String>
        })

        viewModel.categoriesLiveData.observe(viewLifecycleOwner, Observer{data ->
            val c_names = data.map { it.name }.toList()
            Log.i("nnames", c_names[1])
            c_adapter.updateData(c_names)
        })

        viewModel.restaurantData.observe(viewLifecycleOwner, Observer { rows ->
            adapter.updateData(rows)
        })

        viewModel.bannerGet()
        viewModel.bannerList.observe(viewLifecycleOwner, Observer{data->
            b_adapter.updateData(data)
        })
        recyclerView.getItemAnimator()?.endAnimations();
        c_recyclerView.adapter = c_adapter
        recyclerView.adapter = adapter
        b_recyclerView.adapter = b_adapter

        view.findViewById<TextView>(R.id.search).setOnClickListener {
            val fragmentB = ViewAllFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
           transaction.setCustomAnimations(
                R.anim.slide_in, // enter
                R.anim.fade_out, // exit
                R.anim.fade_in, // popEnter
                R.anim.slide_out // popExit
            )
            transaction.replace(
                R.id.frame_main,
                fragmentB
            )
            val bundle = Bundle().apply {
                putBoolean("MY_BOOLEAN_KEY", true)
            }

            fragmentB.arguments = bundle
            transaction.addToBackStack(null)
            transaction.commit()
        }

        view.findViewById<TextView>(R.id.view_all).setOnClickListener {
            val fragmentB = ViewAllFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(
                R.anim.slide_in, // enter
                R.anim.fade_out, // exit
                R.anim.fade_in, // popEnter
                R.anim.slide_out // popExit
            )
            transaction.replace(
                R.id.frame_main,
                fragmentB
            )
            val bundle = Bundle().apply {
                putBoolean("MY_BOOLEAN_KEY", false)
            }
            fragmentB.arguments = bundle
            transaction.addToBackStack(null)
            transaction.commit()
        }


        return view
    }

    override fun onItemClick(data: Row) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val newFragment = RestaurantDetailedFragment()
        val bundle = Bundle().apply {
            putParcelable("data_key", data)
        }
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
