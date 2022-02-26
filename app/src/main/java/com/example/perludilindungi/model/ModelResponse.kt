package com.example.perludilindungi.model

import android.view.ViewDebug

data class ModelResponse(
    val key: String,
    val value: String,
) {
    override fun toString(): String {
        return value
    }
}
