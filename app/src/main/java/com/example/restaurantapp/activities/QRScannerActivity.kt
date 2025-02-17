package com.example.restaurantapp.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.restaurantapp.R
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CompoundBarcodeView


import com.budiyev.android.codescanner.*

class QRScannerActivity : AppCompatActivity() {
    private lateinit var codeScanner: CodeScanner

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrscanner)

        val scannerView: CodeScannerView = findViewById(R.id.barcode_scanner)
        val btn = findViewById<AppCompatButton>(R.id.cancel_btn)
        btn.setOnClickListener{
            fragmentManager.popBackStack() // Use supportFragmentManager if you're using support fragments

        }
        codeScanner = CodeScanner(this, scannerView)

        // Set callback for scanned QR codes
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                // Handle scanned QR code
                Toast.makeText(this, "Scanned: ${it.text}", Toast.LENGTH_SHORT).show()
            }
        }

        // Start camera preview
        codeScanner.startPreview()
    }

    override fun onResume() {
        super.onResume()
        // Resume camera preview
        codeScanner.startPreview()
    }

    override fun onPause() {
        // Pause camera preview
        codeScanner.releaseResources()
        super.onPause()
    }
}
