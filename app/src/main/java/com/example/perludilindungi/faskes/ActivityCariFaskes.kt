package com.example.perludilindungi.faskes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.perludilindungi.R
import com.example.perludilindungi.repository.Repository

class ActivityCariFaskes : AppCompatActivity() {

    private lateinit var viewModel: CariFaskesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cari_faskes)
        Log.i("[LOG INFO]","onCreate called")

        // get reference to the autocomplete text view
        val autoCompleteTVProvince = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextViewProvinsi)
        val autoCompleteTVCity = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextViewKota)
        val buttonSearch = findViewById<Button>(R.id.buttonSearch)

        // PROVINCES
        val repository = Repository()
        val viewModelFactory = CariFaskesViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CariFaskesViewModel::class.java)
        viewModel.getProvince()
        viewModel.myProvinceResponse.observe(this, Observer { response ->
            if(response.isSuccessful) {
//                Log.d("Response", response.body()?.curr_val!!)
//                Log.d("Response", response.body()?.message!!)
//                Log.d("Response", response.body()?.results.toString())
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

        autoCompleteTVProvince.setOnItemClickListener { adapterView, view, i, l ->
            val provinceName = autoCompleteTVProvince.text.toString()
            viewModel.getCity(provinceName)
            viewModel.myCityResponse.observe(this, Observer { response ->
                if(response.isSuccessful) {
//                    Log.d("Response", response.body()?.curr_val!!)
//                    Log.d("Response", response.body()?.message!!)
//                    Log.d("Response", response.body()?.results.toString())
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
            viewModel.getFaskes(provinceInput, cityInput)
            viewModel.myFaskesResponse.observe(this, { response ->
                if(response.isSuccessful) {
                    Log.d("FASKES", response.body().toString())
                } else {
                    Log.d("Response", response.errorBody().toString())
                }
            })
        }
    }
}