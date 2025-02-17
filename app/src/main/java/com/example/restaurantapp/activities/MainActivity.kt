package com.example.restaurantapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.ContentView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.restaurantapp.R
import com.example.restaurantapp.databinding.ActivityMainBinding
import com.example.restaurantapp.fragments.*
import com.example.restaurantapp.activities.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar: ActionBar? = supportActionBar

        if (actionBar != null) {
            actionBar.hide()
        }

        replaceFragment(HomeFragment())

        findViewById<BottomNavigationView>(R.id.bottom_nav).setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_home -> replaceFragment(HomeFragment())
                R.id.item_liked -> replaceFragment(LikedFragment()) // Replace with LikedFragment if list is not empty
                R.id.item_qr -> { startActivity(Intent(this, QRScannerActivity::class.java)) }
                R.id.item_account -> replaceFragment(AccountAuthorizedFragment())
                R.id.item_history -> replaceFragment(HistoryFragment())
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_main, fragment)
            .addToBackStack(null) // Optional: This allows the user to navigate back to the previous fragment
            .commit()
    }
}
