package com.example.restaurantapp.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import com.example.restaurantapp.LocalStorage
import com.example.restaurantapp.R
import com.example.restaurantapp.activities.LoginActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class LogOutMessageDialog : BottomSheetDialogFragment() {
    private lateinit var localStorage: LocalStorage

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        localStorage = LocalStorage(requireActivity().application)

        val view = inflater.inflate(R.layout.fragment_log_out_message_dialog, container, false)
        val yes = view.findViewById<AppCompatButton>(R.id.yes_btn)
        val no = view.findViewById<AppCompatButton>(R.id.no_btn)

        yes.setOnClickListener{
            localStorage.saveString("cardId", "")
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }
        no.setOnClickListener{
            isCancelable = true
            dismiss() // Dismiss the dialog

        }

        return view
    }

}