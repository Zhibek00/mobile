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
import com.example.restaurantapp.LocalStorage
import com.example.restaurantapp.R
import com.example.restaurantapp.viewmodels.RestaurantViewModel
import com.example.restaurantapp.viewmodels.UserViewModel


class CardUITrueFragment : Fragment() {
    private lateinit var localStorage: LocalStorage
    private lateinit var viewModel: UserViewModel

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        localStorage = LocalStorage(requireContext())
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(UserViewModel::class.java)

        val view = inflater.inflate(R.layout.fragment_card_u_i_true, container, false)
        val cardNumber = view.findViewById<TextView>(R.id.cardNumber)
        val date = view.findViewById<TextView>(R.id.date)
        val deleteBtn = view.findViewById<AppCompatButton>(R.id.delete_btn)
        val cardId = localStorage.getString("cardId","")
        if(cardId.isNotEmpty()){
            viewModel.cardGet(cardId)
            viewModel.checkerResponse.observe(viewLifecycleOwner){ checker ->
                if(checker){
                    viewModel.cardResponse.observe(viewLifecycleOwner) { data ->
                        cardNumber.text = data.cardNumber.toString()
                        date.text = data.validthru
                    }
                }

            }
        }

        deleteBtn.setOnClickListener {
            viewModel.cardDelete(cardId)
            viewModel.checkerResponse.observe(viewLifecycleOwner, Observer { checker ->
                if(checker){
                    val fragmentB = CardUIFragment()
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
                })
        }

        return view
    }


}