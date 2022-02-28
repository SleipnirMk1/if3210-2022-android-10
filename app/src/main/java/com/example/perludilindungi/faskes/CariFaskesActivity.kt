package com.example.perludilindungi.faskes

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.perludilindungi.R
import com.example.perludilindungi.adapter.FaskesAdapter
import com.example.perludilindungi.faskes.fragments.FaskesFragment
import com.example.perludilindungi.repository.Repository
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnCompleteListener
import java.util.jar.Manifest

class CariFaskesActivity : AppCompatActivity(){

    private lateinit var viewModel: CariFaskesViewModel
    private lateinit var flp: FusedLocationProviderClient
    private var lat: Double = 0.0
    private var long: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cari_faskes)
        Log.i("[LOG INFO]","onCreate called")

        // location
        flp = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            // when both permission are granted
            getCurrentLocation()
        } else {
            val arrString: Array<String> = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)

            ActivityCompat.requestPermissions(this, arrString, 100)
        }

        // get reference to the autocomplete text view
        val autoCompleteTVProvince = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextViewProvinsi)
        val autoCompleteTVCity = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextViewKota)
        val buttonSearch = findViewById<Button>(R.id.buttonSearch)

        val repository = Repository()
        val viewModelFactory = CariFaskesViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CariFaskesViewModel::class.java)

        // PROVINCES
        viewModel.getProvince()
        viewModel.myProvinceResponse.observe(this, Observer { response ->
            if(response.isSuccessful) {
                val arrProvince = response.body()?.results
                val arrProvinceString = ArrayList<String>()

                if (arrProvince != null) {
                    for (item in arrProvince) {
                        arrProvinceString.add(item.toString())
                    }
                }

                val arrayAdapterProvince = ArrayAdapter(this, R.layout.dropdown_item, arrProvinceString)
                autoCompleteTVProvince.setAdapter(arrayAdapterProvince)

            } else {
                Log.d("Response", response.errorBody().toString())
            }
        })

        // CITY
        autoCompleteTVProvince.setOnItemClickListener { adapterView, view, i, l ->
            val provinceName = autoCompleteTVProvince.text.toString()
            viewModel.getCity(provinceName)
            viewModel.myCityResponse.observe(this, Observer { response ->
                if(response.isSuccessful) {
                    val arrCity = response.body()?.results
                    val arrCityString = ArrayList<String>()

                    if (arrCity != null) {
                        for (item in arrCity) {
                            arrCityString.add(item.toString())
                        }
                    }

                    val arrayAdapterProvince = ArrayAdapter(this, R.layout.dropdown_item, arrCityString)
                    autoCompleteTVCity.setAdapter(arrayAdapterProvince)

                } else {
                    Log.d("Response", response.errorBody().toString())
                }
            })
        }

        buttonSearch.setOnClickListener {
            val provinceInput = autoCompleteTVProvince.text.toString()
            val cityInput = autoCompleteTVCity.text.toString()

            val mFragmentManager = supportFragmentManager
            val mFragmentTransaction = mFragmentManager.beginTransaction()
            val mFragment = FaskesFragment()

            // send data to fragment
            val mBundle = Bundle()
            mBundle.putDouble("lat", lat)
            mBundle.putDouble("long", long)
            mBundle.putString("provinceInput", provinceInput)
            mBundle.putString("cityInput", cityInput)
            mFragment.arguments = mBundle
            mFragmentTransaction.replace(R.id.frameLayoutFaskes, mFragment).commit()
        }
    }

    @Suppress("DEPRECATION")
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
                    val locationRequest = LocationRequest()
                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                        .setInterval(10000)
                        .setFastestInterval(1000)
                        .setNumUpdates(1)

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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 &&
                grantResults.size>0 &&
                grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation()
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }
}