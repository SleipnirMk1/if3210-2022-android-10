package com.example.perludilindungi.models

data class CheckInResponse(
    val success: Boolean,
    val code: Integer,
    val message: String,
    val data: CheckInResponseData?
)
