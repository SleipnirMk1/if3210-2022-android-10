package com.example.perludilindungi.faskes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perludilindungi.models.FaskesResponse
import com.example.perludilindungi.models.ProvinceCityResponse
import com.example.perludilindungi.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class CariFaskesViewModel(private val repository: Repository) : ViewModel() {

    val myProvinceResponse: MutableLiveData<Response<ProvinceCityResponse>> = MutableLiveData()
    val myCityResponse: MutableLiveData<Response<ProvinceCityResponse>> = MutableLiveData()
    val myFaskesResponse: MutableLiveData<Response<FaskesResponse>> = MutableLiveData()

    fun getProvince() {
        viewModelScope.launch {
            val response = repository.getProvince()
            myProvinceResponse.value = response
        }
    }

    fun getCity(provinceName: String) {
        viewModelScope.launch {
            val response = repository.getCity(provinceName)
            myCityResponse.value = response
        }
    }

    fun getFaskes(province: String, city: String) {
        viewModelScope.launch {
            val response = repository.getFaskes(province, city)
            myFaskesResponse.value = response
        }
    }
}