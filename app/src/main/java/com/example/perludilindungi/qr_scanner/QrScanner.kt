package com.example.perludilindungi.qr_scanner

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.*
import androidx.core.app.ActivityCompat
import com.example.perludilindungi.R
import com.example.perludilindungi.models.CheckInPost
import com.google.zxing.integration.android.IntentIntegrator
import java.util.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.perludilindungi.repository.Repository
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnCompleteListener

// NOTE: TOLONG ADA YG TES TEMPERATURE, DEVICE GW GAADA SENSOR TEMPERATURE
class QrScanner : AppCompatActivity(), SensorEventListener {
    private lateinit var outDisplay: TextView
    private lateinit var tempDisplay: TextView
    private lateinit var reasonDisplay: TextView
    private lateinit var statusDisplay: ImageView
    private lateinit var sensorManager: SensorManager
    private lateinit var viewModel: QrScannerViewModel
    private lateinit var flp: FusedLocationProviderClient
    private var noSensor: Boolean = false
    private var lat: Double = 0.0
    private var long: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_scanner)

        tempDisplay = findViewById<TextView>(R.id.temperatureText)
        outDisplay = findViewById<TextView>(R.id.output)
        reasonDisplay = findViewById<TextView>(R.id.reasonText)
        statusDisplay = findViewById<ImageView>(R.id.statusImage)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        val repo = Repository()
        val viewModelFactory = QrScannerViewModelFactory(repo)
        viewModel = ViewModelProvider(this, viewModelFactory).get(QrScannerViewModel::class.java)

        flp = LocationServices.getFusedLocationProviderClient(this)

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
            getCurrentLocation()
            val checkInPost = CheckInPost(result.contents, lat, long)
            viewModel.postQr(checkInPost)
            viewModel.checkInResponse.observe(this, Observer { response ->
                if (response.isSuccessful) {
                    var responseData = response.body()?.data
                    if (responseData != null) {
                        if (responseData.userStatus == "green") {
                            statusDisplay.setImageResource(R.drawable.ic_action_name)
                            outDisplay.text = "Berhasil"
                            reasonDisplay.text = ""
                        } else if (responseData.userStatus == "yellow") {
                            statusDisplay.setImageResource(R.drawable.ic_baseline_remove_circle_24)
                            outDisplay.text = "Hati-hati"
                            reasonDisplay.text = ""
                        } else if (responseData.userStatus == "red") {
                            statusDisplay.setImageResource(R.drawable.ic_action_cancel)
                            outDisplay.text = "Gagal"
                            reasonDisplay.text = responseData.reason
                        } else if (responseData.userStatus == "black") {
                            statusDisplay.setImageResource(R.drawable.ic_baseline_error_24)
                            outDisplay.text = "Bahaya"
                            reasonDisplay.text = responseData.reason
                        }
                    }
                } else {
                    outDisplay.text = "Terjadi error"
                }
            })
        }
    }

    // Sama persis seperti di cari faskes
    // tidak menggunakan intent karena perlu dapat diakses di tempat lain
    fun getCurrentLocation() {
        // initialize location
        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            ||locationManager.isProviderEnabled((LocationManager.NETWORK_PROVIDER))) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            flp.lastLocation.addOnCompleteListener(OnCompleteListener {
                val location: Location = it.result

                if (location != null) {
                    lat = location.latitude
                    long = location.longitude
                    Log.d("PROVIdER", location.provider)
                }
                else {
//                    val locationRequest = LocationRequest()
//                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
//                        .setInterval(10000)
//                        .setFastestInterval(1000)
//                        .setNumUpdates(1)
                    val locationRequest = LocationRequest.create().apply {
                        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                        interval = 10000
                        fastestInterval = 1000
                        numUpdates = 1
                    }

                    // initialize location callback
                    class LocationCallbackFaskes : LocationCallback() {
                        override fun onLocationResult(p0: LocationResult) {
                            super.onLocationResult(p0)
                            val loc = p0.lastLocation
                            lat = loc.latitude
                            long = loc.longitude
                        }
                    }
                    val locationCallBack = LocationCallbackFaskes()

                    flp.requestLocationUpdates(locationRequest, locationCallBack,
                        Looper.myLooper()!!
                    )
                }
            })
        }

        else {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
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
            tempDisplay.text = "No Sensor"
        }
    }

    fun unregisterAll() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if (p0 != null) {
            if (p0.values.size > 0) {
                tempDisplay.text = "Temp: ${p0.values[0]}"
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        // do nothing
    }
}