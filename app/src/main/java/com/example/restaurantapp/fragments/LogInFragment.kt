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
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import com.example.restaurantapp.R
import com.example.restaurantapp.activities.MainActivity
import com.example.restaurantapp.viewmodels.RestaurantViewModel
import com.example.restaurantapp.viewmodels.UserViewModel

class LogInFragment : Fragment() {
    private lateinit var viewModel: UserViewModel

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_log_in, container, false)
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(UserViewModel::class.java)
        val btn = view.findViewById<AppCompatButton>(R.id.log_in_btn)
        val noAccountText = view.findViewById<TextView>(R.id.no_account_txt)


        noAccountText.setOnClickListener {
            replaceFragment(SignUpFragment())

        }
        btn.setOnClickListener {
            // Get the phone number and password entered by the user
            val phoneNumberEditText = view.findViewById<EditText>(R.id.phone)
            val passwordEditText = view.findViewById<EditText>(R.id.password)

            val phoneNumber = phoneNumberEditText?.text?.toString()
            val password = passwordEditText?.text?.toString()

            if (phoneNumber != null && password != null) {
                viewModel.login(phoneNumber, password)
            } else {
                Log.e("Registration", "One of the EditText objects is null${phoneNumber}")
            }
        }
        viewModel.loginResponse.observe(viewLifecycleOwner) { loginResponse ->
            if (loginResponse != null) {
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
            }
        }
        // Inflate the layout for this fragment
        return view
    }
    private fun replaceFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.frame_authorization, fragment)
            .addToBackStack(null) // Optional: This allows the user to navigate back to the previous fragment
            .commit()
    }

}