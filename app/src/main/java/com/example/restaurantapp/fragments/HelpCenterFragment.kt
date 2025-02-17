package com.example.restaurantapp.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.findFragment
import com.example.restaurantapp.R


class HelpCenterFragment : Fragment() {


    @SuppressLint("MissingInflatedId", "IntentReset")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_help_center, container, false)
        val title = view.findViewById<EditText>(R.id.title)
        val request = view.findViewById<EditText>(R.id.request)
        val saveBtn = view.findViewById<AppCompatButton>(R.id.save_btn)

        saveBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.data = Uri.parse("mailto:") // Only email apps should handle this
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("200103281@stu.sdu.edu.kz"))
            intent.putExtra(Intent.EXTRA_SUBJECT, title.text.toString())
            intent.putExtra(Intent.EXTRA_TEXT, request.text.toString())
            startActivity(intent)
        }

        return view
    }


}