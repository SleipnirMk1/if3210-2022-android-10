package com.example.perludilindungi.models

data class ProvinceCityResponse (
    val curr_val: String,
    val message: String,
    val results: ArrayList<ModelResponse> // antara pake list atau array
)