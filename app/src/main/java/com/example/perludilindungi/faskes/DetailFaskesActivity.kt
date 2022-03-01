package com.example.perludilindungi.faskes

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.perludilindungi.R
import com.example.perludilindungi.adapter.FaskesAdapter

class DetailFaskesActivity : AppCompatActivity(){

    private val TAG = "DetailFaskesActivity"
    private var namaFaskes = ""
    private var kodeFaskes = ""
    private var alamatFaskes = ""
    private var telpFaskes = ""
    private var jenisFaskes = ""
    private var statusFaskes = ""
    private var lon = ""
    private var lat = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_faskes)
        Log.d(TAG, "onCreate: started.")
        getIncomingIntent()

        val gMapsButton = findViewById<Button>(R.id.buttonGoogleMaps2)

        gMapsButton.setOnClickListener {
            Log.d(TAG, "onCreate: gmaps button clicked")

            // Create a Uri from an intent string. Use the result to create an Intent.
            val stringUri = "geo:$lat,$lon?q=$namaFaskes, $alamatFaskes"
            Log.d(TAG, "onCreate: $stringUri")
            val gmmIntentUri = Uri.parse(stringUri)

            // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            // Make the Intent explicit by setting the Google Maps package
            mapIntent.setPackage("com.google.android.apps.maps")

            // Attempt to start an activity that can handle the Intent
            startActivity(mapIntent)
        }
    }

    fun getIncomingIntent() {
        Log.d(TAG, "getIncomingIntent: checking for incoming intents")
        if (intent.hasExtra("namaFaskes") &&
            intent.hasExtra("kodeFaskes") &&
            intent.hasExtra("alamatFaskes") &&
            intent.hasExtra("telpFaskes") &&
            intent.hasExtra("jenisFaskes") &&
            intent.hasExtra("statusFaskes") &&
                intent.hasExtra("lon") &&
                intent.hasExtra("lat")) {
            Log.d(TAG, "getIncomingIntent: found intent extras")

            namaFaskes = intent.getStringExtra("namaFaskes").toString()
            kodeFaskes = intent.getStringExtra("kodeFaskes").toString()
            alamatFaskes = intent.getStringExtra("alamatFaskes").toString()
            telpFaskes = intent.getStringExtra("telpFaskes").toString()
            jenisFaskes = intent.getStringExtra("jenisFaskes").toString()
            statusFaskes = intent.getStringExtra("statusFaskes").toString()
            lon = intent.getStringExtra("lon").toString()
            lat = intent.getStringExtra("lat").toString()

            setFaskes()
        }
    }

    fun setFaskes() {
        Log.d(TAG, "setFaskes: setting faskes data to widgets")

        val tvNamaFaskes = findViewById<TextView>(R.id.tvNamaFaskes2)
        val tvKodeFaskes = findViewById<TextView>(R.id.tvKodeFaskes2)
        val tvAlamatFaskes = findViewById<TextView>(R.id.tvAlamatFaskes2)
        val tvTelpFaskes = findViewById<TextView>(R.id.tvNoTelpFaskes2)
        val tvJenisFaskes = findViewById<TextView>(R.id.tvTipeFaskes2)
        val tvStatusFaskes = findViewById<TextView>(R.id.tvStatusFaskes2)

        tvNamaFaskes.text = namaFaskes
        tvKodeFaskes.text = kodeFaskes
        tvAlamatFaskes.text = alamatFaskes
        tvTelpFaskes.text = telpFaskes
        tvJenisFaskes.text = jenisFaskes
        tvStatusFaskes.text = statusFaskes
    }
}