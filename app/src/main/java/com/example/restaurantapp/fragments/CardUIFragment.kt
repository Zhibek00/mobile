package com.example.restaurantapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import com.example.restaurantapp.R

class CardUIFragment : Fragment() {


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_card_u_i, container, false)
        val btn = view.findViewById<AppCompatButton>(R.id.add_card_btn)
        val receivedBoolean = arguments?.getBoolean("MY_BOOLEAN_KEY", false)


        btn.setOnClickListener {
            val fragmentB = CardFragment()
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
            transaction.addToBackStack(null)
            transaction.commit()
        }
        return view
    }


}