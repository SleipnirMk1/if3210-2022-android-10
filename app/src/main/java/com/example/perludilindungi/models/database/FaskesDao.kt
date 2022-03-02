package com.example.perludilindungi.models.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.perludilindungi.models.DataFaskesResponse

@Dao
interface FaskesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFaskes(dataFaskes: DataFaskesResponse)

    @Query("SELECT * FROM bookmark_faskes")
    fun readAllData(): LiveData<List<DataFaskesResponse>>

    @Query("SELECT EXISTS (SELECT 1 FROM bookmark_faskes WHERE id = :id)")
    fun isBookmarked(id: Int): LiveData<Boolean>

    @Delete
    suspend fun unbookmark(dataFaskes: DataFaskesResponse)
}