package com.example.perludilindungi.qr_scanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.perludilindungi.R
import com.google.zxing.integration.android.IntentIntegrator

class QrScanner : AppCompatActivity() {
    private lateinit var outDisplay: TextView
    private lateinit var tempDisplay: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_scanner)

        tempDisplay = findViewById<TextView>(R.id.temperatureText)
        outDisplay = findViewById<TextView>(R.id.output)
        val scanBtn = findViewById<Button>(R.id.scanButton)

        scanBtn.setOnClickListener {
            val intentIntegrator = IntentIntegrator(this)
            intentIntegrator.setDesiredBarcodeFormats(listOf(IntentIntegrator.QR_CODE))
            intentIntegrator.initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var result = IntentIntegrator.parseActivityResult(resultCode, data)
        if (result != null) {
            outDisplay.text = result.contents
        }
    }
}