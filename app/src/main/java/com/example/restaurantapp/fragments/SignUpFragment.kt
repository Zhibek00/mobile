package com.example.restaurantapp.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.restaurantapp.R
import com.example.restaurantapp.activities.MainActivity
import com.example.restaurantapp.viewmodels.RestaurantViewModel
import com.example.restaurantapp.viewmodels.UserViewModel
import org.w3c.dom.Text


class SignUpFragment : Fragment() {
    private lateinit var viewModel: UserViewModel

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        val btn = view.findViewById<Button>(R.id.sign_up_btn)
        val haveAccountText = view.findViewById<TextView>(R.id.have_account_txt)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(UserViewModel::class.java)
        haveAccountText.setOnClickListener {
            replaceFragment(LogInFragment())
        }
        btn.setOnClickListener {
            val fullNameEditText = view.findViewById<EditText>(R.id.full_name)
            val phoneNumberEditText = view.findViewById<EditText>(R.id.phone)
            val passwordEditText = view.findViewById<EditText>(R.id.password)

            val fullName = fullNameEditText?.text?.toString()
            val phoneNumber = phoneNumberEditText?.text?.toString()
            val password = passwordEditText?.text?.toString()

            // Check for null before proceeding
            if (fullName != null && phoneNumber != null && password != null) {
                // Call the register function with the provided data
                viewModel.register(fullName, phoneNumber, password)
            } else {
                // Handle null values or show an error message
                Log.e("Registration", "One of the EditText objects is null${phoneNumber}")
            }
        }
        viewModel.checkerResponse.observe(this) { registrationSuccessful ->
            if (registrationSuccessful) {
                // Registration was successful, start new activity
                replaceFragment(LogInFragment())
            } else {
            }
        }
        return view
    }
    private fun replaceFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.frame_authorization, fragment)
            .addToBackStack(null) // Optional: This allows the user to navigate back to the previous fragment
            .commit()
    }



}