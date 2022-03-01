package com.example.perludilindungi.models.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.perludilindungi.models.DataFaskesResponse

@Dao
interface FaskesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFaskes(dataFaskes: DataFaskesResponse)

    @Query("SELECT * FROM bookmark_faskes")
    fun readAllData(): LiveData<List<DataFaskesResponse>>


}