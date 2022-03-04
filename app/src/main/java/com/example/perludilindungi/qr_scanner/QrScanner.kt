package com.example.perludilindungi.qr_scanner

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.perludilindungi.R
import com.example.perludilindungi.models.CheckInPost
import com.google.zxing.integration.android.IntentIntegrator
import java.util.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.perludilindungi.faskes.CariFaskesViewModel
import com.example.perludilindungi.repository.Repository

// NOTE: TOLONG ADA YG TES TEMPERATURE, DEVICE GW GAADA SENSOR TEMPERATURE
class QrScanner : AppCompatActivity(), SensorEventListener {
    private lateinit var outDisplay: TextView
    private lateinit var tempDisplay: TextView
    private lateinit var sensorManager: SensorManager
    private lateinit var viewModel: QrScannerViewModel
    private var noSensor: Boolean = false
    private var lat: Double = 0.0
    private var long: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_scanner)

        lat = intent.getDoubleExtra("latitude", 0.0)
        long = intent.getDoubleExtra("longitude", 0.0)

        tempDisplay = findViewById<TextView>(R.id.temperatureText)
        outDisplay = findViewById<TextView>(R.id.output)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        val repo = Repository()
        val viewModelFactory = QrScannerViewModelFactory(repo)
        viewModel = ViewModelProvider(this, viewModelFactory).get(QrScannerViewModel::class.java)

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
            val checkInPost = CheckInPost(result.contents, lat, long)
            viewModel.postQr(checkInPost)
            viewModel.checkInResponse.observe(this, Observer { response ->
                if (response.isSuccessful) {
                    // display message
                } else {
                    // display message
                }
                outDisplay.text = response.message()
            })
        }
    }

    override fun onResume() {
        super.onResume()
        loadAmbientTemperature()
    }

    override fun onPause() {
        super.onPause()
        unregisterAll()
    }

    fun loadAmbientTemperature() {
        var sensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)

        if (sensor != null) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST)
        } else if (!noSensor) {
            noSensor = true
            Toast.makeText(this, "No Ambient Temperature Sensor !", Toast.LENGTH_LONG).show()
        }
    }

    fun unregisterAll() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if (p0 != null) {
            if (p0.values.size > 0) {
                tempDisplay.text = "${p0.values[0]}"
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        // do nothing
    }
}