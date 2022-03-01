package com.example.perludilindungi.faskes

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.perludilindungi.R
import com.example.perludilindungi.adapter.FaskesAdapter
import com.example.perludilindungi.models.DataFaskesResponse
import com.example.perludilindungi.models.database.FaskesDataViewModel
import com.example.perludilindungi.models.database.FaskesRepository
import com.google.android.material.bottomnavigation.BottomNavigationView

class DetailFaskesActivity : AppCompatActivity(){

    private val TAG = "DetailFaskesActivity"

    private var id = -1
    private var namaFaskes = ""
    private var kodeFaskes = ""
    private var kota = ""
    private var provinsi = ""
    private var alamatFaskes = ""
    private var telpFaskes = ""
    private var jenisFaskes = ""
    private var statusFaskes = ""
    private var lon = ""
    private var lat = ""

    private lateinit var mFaskesViewModel: FaskesDataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_faskes)
        Log.d(TAG, "onCreate: started.")
        getIncomingIntent()

        val gMapsButton = findViewById<Button>(R.id.buttonGoogleMaps2)
        val bookmarkButton = findViewById<Button>(R.id.buttonBookmark2)

        mFaskesViewModel= ViewModelProvider(this).get(FaskesDataViewModel::class.java)

        gMapsButton.setOnClickListener {
            Log.d(TAG, "onCreate: gmaps button clicked")
            gMapsIntent()
        }

        bookmarkButton.setOnClickListener {
            insertDataToDatabase()
        }

        val navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navigation.selectedItemId = R.id.navigationLocation
        navigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigationBookmark -> {
                    startActivity(Intent(this, BookmarkFaskesActivity::class.java))
                }
                R.id.navigationLocation -> {
                    startActivity(Intent(this, CariFaskesActivity::class.java))
                }
                R.id.navigationNews -> {
                    // TODO taroh activity news disini
                }
                else -> Log.d(TAG, "onCreate: masuk else")
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun insertDataToDatabase() {
        if(allExtrasAvail()) {
            // create object
            val faskesObject = DataFaskesResponse(
                id,
                kodeFaskes,
                namaFaskes,
                kota,
                provinsi,
                alamatFaskes,
                lat,
                lon,
                telpFaskes,
                jenisFaskes,
                statusFaskes
            )
            mFaskesViewModel.addFaskes(faskesObject)
            Toast.makeText(this, "Successfully added to bookmark!", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Failed to add faskes to bookmark", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getIncomingIntent() {
        Log.d(TAG, "getIncomingIntent: checking for incoming intents")
        if (allExtrasAvail()) {
            Log.d(TAG, "getIncomingIntent: found intent extras")

            id = intent.getIntExtra("idFaskes", 0)
            namaFaskes = intent.getStringExtra("namaFaskes").toString()
            kodeFaskes = intent.getStringExtra("kodeFaskes").toString()
            kota = intent.getStringExtra("kotaFaskes").toString()
            provinsi = intent.getStringExtra("provinsiFaskes").toString()
            alamatFaskes = intent.getStringExtra("alamatFaskes").toString()
            telpFaskes = intent.getStringExtra("telpFaskes").toString()
            jenisFaskes = intent.getStringExtra("jenisFaskes").toString()
            statusFaskes = intent.getStringExtra("statusFaskes").toString()
            lon = intent.getStringExtra("lon").toString()
            lat = intent.getStringExtra("lat").toString()

            setFaskes()
        }
    }

    private fun allExtrasAvail(): Boolean {
        return (intent.hasExtra("idFaskes") &&
                intent.hasExtra("namaFaskes") &&
                intent.hasExtra("kodeFaskes") &&
                intent.hasExtra("kotaFaskes") &&
                intent.hasExtra("provinsiFaskes") &&
                intent.hasExtra("alamatFaskes") &&
                intent.hasExtra("telpFaskes") &&
                intent.hasExtra("jenisFaskes") &&
                intent.hasExtra("statusFaskes") &&
                intent.hasExtra("lon") &&
                intent.hasExtra("lat"))
    }

    private fun setFaskes() {
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

    private fun gMapsIntent() {
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