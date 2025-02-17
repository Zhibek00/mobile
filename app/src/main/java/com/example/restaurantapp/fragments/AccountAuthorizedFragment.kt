package com.example.restaurantapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.transition.TransitionInflater
import com.example.restaurantapp.LocalStorage
import com.example.restaurantapp.R
import com.example.restaurantapp.activities.LoginActivity


class AccountAuthorizedFragment : Fragment() {
    private lateinit var localStorage: LocalStorage


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_account_authorized, container, false)
        localStorage = LocalStorage(requireContext())
        val profileEditBtn = view.findViewById<FrameLayout>(R.id.profile_edit_btn)
        val paymentEditBtn = view.findViewById<FrameLayout>(R.id.payment_btn)
        val passwordEditBtn = view.findViewById<FrameLayout>(R.id.password_btn)
        val helpCenterEditBtn = view.findViewById<FrameLayout>(R.id.help_btn)

        val logOutBtn = view.findViewById<Button>(R.id.log_out_btn)

        val fullName = view.findViewById<TextView>(R.id.full_name)

        val nameValue = localStorage.getString("fullName", "null")
        fullName.text = nameValue

        profileEditBtn.setOnClickListener {
            replaceFragment(AccountDetailFragment())
        }
        paymentEditBtn.setOnClickListener {
            if(localStorage.getString("cardId").isEmpty()){
                replaceFragment(CardUIFragment())
            }
            else{
                replaceFragment(CardUITrueFragment())
            }
        }
        passwordEditBtn.setOnClickListener {
            replaceFragment(PasswordFragment())

        }
        helpCenterEditBtn.setOnClickListener {
            replaceFragment(HelpCenterFragment())

        }


        logOutBtn.setOnClickListener {
            val bottomSheetDialogFragment = LogOutMessageDialog()
            bottomSheetDialogFragment.show(childFragmentManager, bottomSheetDialogFragment.tag)
        }

        return view
    }

    fun replaceFragment(fragment: Fragment){
        val fragmentB = fragment
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
        ) // R.id.fragment_container is the id of the container in your layout
        transaction.addToBackStack(null) // Optionally add to back stack, so the user can navigate back to FragmentA
        transaction.commit()
    }

}