package com.example.perludilindungi.faskes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_faskes)
        Log.d(TAG, "onCreate: started.")
        getIncomingIntent()
    }

    fun getIncomingIntent() {
        Log.d(TAG, "getIncomingIntent: checking for incoming intents")
        if (intent.hasExtra("namaFaskes") &&
            intent.hasExtra("kodeFaskes") &&
            intent.hasExtra("alamatFaskes") &&
            intent.hasExtra("telpFaskes") &&
            intent.hasExtra("jenisFaskes") &&
            intent.hasExtra("statusFaskes")) {
            Log.d(TAG, "getIncomingIntent: found intent extras")

            namaFaskes = intent.getStringExtra("namaFaskes").toString()
            kodeFaskes = intent.getStringExtra("kodeFaskes").toString()
            alamatFaskes = intent.getStringExtra("alamatFaskes").toString()
            telpFaskes = intent.getStringExtra("telpFaskes").toString()
            jenisFaskes = intent.getStringExtra("jenisFaskes").toString()
            statusFaskes = intent.getStringExtra("statusFaskes").toString()

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