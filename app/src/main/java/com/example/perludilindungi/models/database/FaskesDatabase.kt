package com.example.perludilindungi.models.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.perludilindungi.models.DataFaskesResponse

@Database(entities = [DataFaskesResponse::class], version = 1, exportSchema = false)
abstract class FaskesDatabase: RoomDatabase() {

    abstract fun faskesDao(): FaskesDao

    companion object {
        @Volatile
        private var INSTANCE: FaskesDatabase? = null

        fun getDatabase(context: Context): FaskesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FaskesDatabase::class.java,
                    "bookmark_faskes"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}