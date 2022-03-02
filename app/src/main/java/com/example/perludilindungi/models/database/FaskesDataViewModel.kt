package com.example.perludilindungi.models.database

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.perludilindungi.models.DataFaskesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import kotlin.properties.Delegates

class FaskesDataViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<DataFaskesResponse>>
//    var isBookmarked: LiveData<Boolean>
    private val repository: FaskesRepository
    private val TAG = "FaskesDataViewModel"

    init {
        val faskesDao = FaskesDatabase.getDatabase(application).faskesDao()
        repository = FaskesRepository(faskesDao)
        readAllData = repository.readAllData
//        isBookmarked = repository.isBookmarked
    }

    fun addFaskes(faskes: DataFaskesResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFaskes(faskes)
        }
    }

    fun unbookmark (faskes: DataFaskesResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.unbookmark(faskes)
        }
    }

    fun isBookmarked (id: Int): Boolean {
        return runBlocking {
            repository.isBookmarked(id)
        }
    }
}