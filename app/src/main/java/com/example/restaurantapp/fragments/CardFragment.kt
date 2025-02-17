package com.example.restaurantapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import com.example.restaurantapp.R
import com.example.restaurantapp.viewmodels.RestaurantViewModel
import com.example.restaurantapp.viewmodels.UserViewModel


class CardFragment : Fragment() {
    private lateinit var viewModel: UserViewModel


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(UserViewModel::class.java)

        val view = inflater.inflate(R.layout.fragment_card, container, false)
        val cardNumber = view.findViewById<EditText>(R.id.card_number_edit)
        val name = view.findViewById<EditText>(R.id.card_name_edit)
        val date = view.findViewById<EditText>(R.id.date_edit)
        val cvv = view.findViewById<EditText>(R.id.cvv_edit)
        val saveBtn = view.findViewById<AppCompatButton>(R.id.save_btn)

        saveBtn.setOnClickListener {
            if(cardNumber.text.toString().isNotEmpty() && name.text.toString().isNotEmpty() && date.text.toString().isNotEmpty() && cvv.text.toString().isNotEmpty()){
                viewModel.cardAdd(cardNumber.text.toString().toLong(), cvv.text.toString().toInt(), date.text.toString())
                viewModel.checkerResponse.observe(viewLifecycleOwner){ checker ->
                    if(checker){
                        val fragmentB = CardUITrueFragment()
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

                }
            }
        }
        return view
    }


}