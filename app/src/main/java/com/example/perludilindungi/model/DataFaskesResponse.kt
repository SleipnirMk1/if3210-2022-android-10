package com.example.perludilindungi.model

data class DataFaskesResponse(
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
    val kelas_rs: String,
    val status: String,
//    val detail: ArrayList<>,
    val source_data: String
)
