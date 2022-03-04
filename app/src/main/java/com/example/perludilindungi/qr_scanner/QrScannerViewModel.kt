package com.example.perludilindungi.qr_scanner

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perludilindungi.models.CheckInPost
import com.example.perludilindungi.models.CheckInResponse
import com.example.perludilindungi.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class QrScannerViewModel(private val repository: Repository) : ViewModel() {

    var checkInResponse: MutableLiveData<Response<CheckInResponse>> = MutableLiveData()

    fun postQr(post: CheckInPost) {
        viewModelScope.launch {
            val response = repository.postQr(post)
            checkInResponse.value = response
        }
    }
}