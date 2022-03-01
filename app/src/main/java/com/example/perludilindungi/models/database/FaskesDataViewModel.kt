package com.example.perludilindungi.models.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.perludilindungi.models.DataFaskesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FaskesDataViewModel(application: Application): AndroidViewModel(application) {

    private val readAllData: LiveData<List<DataFaskesResponse>>
    private val repository: FaskesRepository

    init {
        val faskesDao = FaskesDatabase.getDatabase(application).faskesDao()
        repository = FaskesRepository(faskesDao)
        readAllData = repository.readAllData
    }

    fun addFaskes(faskes: DataFaskesResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFaskes(faskes)
        }
    }
}