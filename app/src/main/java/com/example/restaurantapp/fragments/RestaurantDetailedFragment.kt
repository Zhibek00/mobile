package com.example.restaurantapp.fragments

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.restaurantapp.R
import com.example.restaurantapp.models.Row
import com.example.restaurantapp.viewmodels.DetailedRestaurantViewModel
import com.example.restaurantapp.viewmodels.RestaurantViewModel
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*


class RestaurantDetailedFragment : Fragment() {
    lateinit var receivedData : Row
    private lateinit var viewModel: DetailedRestaurantViewModel


    @SuppressLint("ClickableViewAccessibility", "MissingInflatedId", "SetTextI18n", "CutPasteId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(DetailedRestaurantViewModel::class.java)

        val data: Parcelable? = arguments?.getParcelable("your_object_key")
        val view = inflater.inflate(R.layout.fragment_restaurant_detailed_new, container, false)
        receivedData = arguments?.getParcelable("data_key")!!
      //  val image = view.findViewById<ImageView>(R.id.image)




      /*  Picasso.get().load("http://172.20.10.9:2002/" + receivedData.path )
            .into(image)

        if(receivedData != null) {

            restaurantName.text = receivedData.name


        }*/

        val textViewOverview: TextView = view.findViewById(R.id.overview_txt)
        val textViewReview: TextView = view.findViewById(R.id.review_txt)

      //  val viewPager: ViewPager = view.findViewById(R.id.view_pager1)
      //  val adapter = RestaurantDetailedAdapter(childFragmentManager)
      //  viewPager.adapter = adapter

        replaceFragment(OverviewFragment())
        textViewOverview.setOnClickListener {


            replaceFragment(OverviewFragment())
            textViewOverview.requestFocus()
        }

        textViewReview.setOnClickListener {

            replaceFragment(ReviewFragment())
            textViewOverview.requestFocus()
        }
        return view
    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentB = fragment
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(
            R.id.fragmentContainerView,
            fragmentB
        )
        val bundle = Bundle()

        // Put your object into the bundle
        bundle.putParcelable("data_key", receivedData)
        fragmentB.arguments = bundle

        transaction.commitNow()
    }

}


