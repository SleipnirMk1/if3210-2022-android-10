package com.example.perludilindungi.model

data class FaskesResponse(
    val success: Boolean,
    val message: String,
    val count_total: Int,
    val data: ArrayList<DataFaskesResponse>
)
