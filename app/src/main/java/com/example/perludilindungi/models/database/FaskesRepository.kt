package com.example.perludilindungi.models.database

import androidx.lifecycle.LiveData
import com.example.perludilindungi.models.DataFaskesResponse

class FaskesRepository(
    private val faskesDao: FaskesDao) {

    val readAllData: LiveData<List<DataFaskesResponse>> = faskesDao.readAllData()
//    val isBookmarked: LiveData<Boolean> = faskesDao.isBookmarked

    suspend fun addFaskes(faskes: DataFaskesResponse) {
        faskesDao.addFaskes(faskes)
    }

    suspend fun unbookmark(faskes: DataFaskesResponse) {
        faskesDao.unbookmark(faskes)
    }

}