package com.example.restaurantapp.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import com.example.restaurantapp.LocalStorage
import com.example.restaurantapp.R
import com.example.restaurantapp.viewmodels.RestaurantViewModel
import com.example.restaurantapp.viewmodels.UserViewModel


class PasswordFragment : Fragment() {
    private lateinit var viewModel: UserViewModel
    private lateinit var localStorage: LocalStorage

    override fun onAttach(context: Context) {
        super.onAttach(context)
        localStorage = LocalStorage(requireActivity().application)

    }
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_password, container, false)
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(UserViewModel::class.java)
        val currentPassword = view.findViewById<EditText>(R.id.current_password)
        val newPassword = view.findViewById<EditText>(R.id.new_password)
        val confirmPassword = view.findViewById<EditText>(R.id.confirm_password)
        val saveBtn = view.findViewById<AppCompatButton>(R.id.save_btn)



        currentPassword.setText(localStorage.getString("password"))
        currentPassword.isEnabled = false
        saveBtn.setOnClickListener {
            if (!newPassword.text.toString().isEmpty() && !confirmPassword.text.toString()
                    .isEmpty()
            ) {
                if (newPassword.text.toString().equals(confirmPassword.text.toString())) {
                    viewModel.update(newPassword.text.toString())
                    viewModel.checkerResponse.observe(viewLifecycleOwner){ answer ->
                        if(answer){
                            val fragmentB = AccountAuthorizedFragment()
                            val transaction = requireActivity().supportFragmentManager.beginTransaction()

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

                    }
                }
            }
        }

        return view
    }
}