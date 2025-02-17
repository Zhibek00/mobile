package com.example.restaurantapp.fragments


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.TransitionInflater
import com.example.restaurantapp.LocalStorage
import com.example.restaurantapp.R
import com.example.restaurantapp.activities.LoginActivity
import com.example.restaurantapp.viewmodels.RestaurantViewModel
import java.io.File

class AccountDetailFragment : Fragment() {
    private lateinit var viewModel: RestaurantViewModel
    private lateinit var localStorage: LocalStorage
    private lateinit var imageView: ImageView
    private var selectedImageUri: Uri? = null

    companion object {
        const val PICK_IMAGE_REQUEST = 1
        const val REQUEST_EXTERNAL_STORAGE_PERMISSION = 101
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account_detail, container, false)
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(RestaurantViewModel::class.java)
        localStorage = LocalStorage(requireContext())

        val name = view.findViewById<EditText>(R.id.full_name)
        val phone = view.findViewById<EditText>(R.id.phone_number)
        val logOutBtn = view.findViewById<AppCompatButton>(R.id.log_out_btn)
        val editBtn = view.findViewById<AppCompatButton>(R.id.edit_btn)
        imageView = view.findViewById<ImageView>(R.id.profile_img)
        imageView.isEnabled = false

        // Retrieve user data from LocalStorage
        val nameValue = localStorage.getString("fullName", "")
        val phoneValue = localStorage.getString("phone", "")

        // Update UI with user data
        name.setText(nameValue)
        phone.setText(phoneValue)

        // Handle logout button click
        logOutBtn.setOnClickListener {
            val bottomSheetDialogFragment = LogOutMessageDialog()
            bottomSheetDialogFragment.show(childFragmentManager, bottomSheetDialogFragment.tag)

        }

        // Handle edit button click
        editBtn.setOnClickListener {
            if (editBtn.text.toString() == "Save") {
                editBtn.text = "Edit Profile"
                name.inputType = InputType.TYPE_NULL
                phone.inputType = InputType.TYPE_NULL
                imageView.isEnabled = false
            } else {
                editBtn.text = "Save"
                name.inputType = InputType.TYPE_CLASS_TEXT
                imageView.isEnabled = true
            }
        }

        // Handle image view click (to open gallery)
        imageView.setOnClickListener {
            openGallery()
        }

        return view
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            selectedImageUri?.let { uri ->
                // Save the selected image URI to the global variable
                this.selectedImageUri = uri

                // Convert URI to File
                val imageFile = File(getRealPathFromURI(uri))

                // Call function to upload image file to server
                viewModel.uploadImage(imageFile)
            }
        }
    }

    private fun getRealPathFromURI(uri: Uri): String {
        val cursor = requireActivity().contentResolver.query(uri, null, null, null, null)
        cursor?.use { c ->
            if (c.moveToFirst()) {
                val columnIndex = c.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                return c.getString(columnIndex)
            }
        }
        return ""
    }

    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_EXTERNAL_STORAGE_PERMISSION)
    }
}
