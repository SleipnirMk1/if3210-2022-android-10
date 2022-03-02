package com.example.perludilindungi.faskes

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.perludilindungi.R
import com.example.perludilindungi.adapter.FaskesAdapter
import com.example.perludilindungi.models.DataFaskesResponse
import com.example.perludilindungi.models.database.FaskesDao
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
        mFaskesViewModel= ViewModelProvider(this)
            .get(FaskesDataViewModel::class.java)
        getIncomingIntent()

        val gMapsButton = findViewById<Button>(R.id.buttonGoogleMaps2)
        val bookmarkButton = findViewById<Button>(R.id.buttonBookmark2)

        gMapsButton.setOnClickListener {
            Log.d(TAG, "onCreate: gmaps button clicked")
            gMapsIntent()
        }

        bookmarkButton.setOnClickListener {
            if (bookmarkButton.text == "Bookmark") {
                insertDataToDatabase()
                bookmarkButton.text = "Unbookmark"
            } else {
                deleteDataFromDatabase()
                bookmarkButton.text = "Bookmark"
            }
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
//            Toast.makeText(this, "Successfully added to bookmark", Toast.LENGTH_LONG).show()
            Log.d(TAG, "insertDataToDatabase: successfully added to bookmark")
        } else {
//            Toast.makeText(this, "Failed to add faskes to bookmark", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "insertDataToDatabase: failed to bookmark")
        }
    }

    private fun deleteDataFromDatabase() {
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
        mFaskesViewModel.unbookmark(faskesObject)
//        Toast.makeText(this, "Successfully remove from bookmark", Toast.LENGTH_LONG).show()
            Log.d(TAG, "deleteDataFromDatabase: successfully remove from bookmark")
        } else {
//            Toast.makeText(this, "Failed to unbookmark", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "deleteDataFromDatabase: failed to unbookmark")
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
        if (telpFaskes == "null") {
            telpFaskes = "-"
        }

        val tvNamaFaskes = findViewById<TextView>(R.id.tvNamaFaskes2)
        val tvKodeFaskes = findViewById<TextView>(R.id.tvKodeFaskes2)
        val tvAlamatFaskes = findViewById<TextView>(R.id.tvAlamatFaskes2)
        val tvTelpFaskes = findViewById<TextView>(R.id.tvNoTelpFaskes2)
        val tvJenisFaskes = findViewById<TextView>(R.id.tvTipeFaskes2)
        val tvStatusFaskes = findViewById<TextView>(R.id.tvStatusFaskes2)
        val ivStatusFaskes = findViewById<ImageView>(R.id.imageView)
        val bookmarkButton = findViewById<Button>(R.id.buttonBookmark2)

        tvNamaFaskes.text = namaFaskes
        tvKodeFaskes.text = kodeFaskes
        tvAlamatFaskes.text = alamatFaskes
        tvTelpFaskes.text = telpFaskes
        tvJenisFaskes.text = jenisFaskes
        tvStatusFaskes.text = statusFaskes

        if(statusFaskes == "SIAP VAKSINASI" || statusFaskes == "Siap Vaksinasi") {
            ivStatusFaskes.setImageResource(R.drawable.ic_action_name)
        } else {
            ivStatusFaskes.setImageResource(R.drawable.ic_action_cancel)
        }

    // TODO
        val bookmarked = mFaskesViewModel.isBookmarked(id)
        if (bookmarked) {
            bookmarkButton.text = "Unbookmark"
        } else {
            bookmarkButton.text = "Bookmark"
        }

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