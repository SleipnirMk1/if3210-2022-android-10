package com.example.perludilindungi.faskes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.perludilindungi.repository.Repository

class CariFaskesViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CariFaskesViewModel(repository) as T
    }
}