package com.example.perludilindungi.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmark_faskes")
data class DataFaskesResponse(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val kode: String,
    val nama: String,
    val kota: String,
    val provinsi: String,
    val alamat: String,
    val latitude: String,
    val longitude: String,
    val telp: String,
    val jenis_faskes: String,
//    val kelas_rs: String,
    val status: String,
//    val detail: ArrayList<>,
//    val source_data: String
)
