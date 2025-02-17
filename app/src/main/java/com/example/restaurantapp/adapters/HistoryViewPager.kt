package com.example.restaurantapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.restaurantapp.fragments.LogInFragment
import com.example.restaurantapp.fragments.PreviousHistory
import com.example.restaurantapp.fragments.SignUpFragment
import com.example.restaurantapp.fragments.UpcomingHistory


class HistoryViewPagerAdapter (fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> UpcomingHistory() // Your First Fragment
            1 -> PreviousHistory() // Your Second Fragment
            else -> throw IllegalArgumentException("Invalid position")
        }
    }

    override fun getCount(): Int {
        return 2 // Number of fragments
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Upcoming" // Title for the first tab
            1 -> "Previous" // Title for the second tab
            else -> null
        }
    }
}