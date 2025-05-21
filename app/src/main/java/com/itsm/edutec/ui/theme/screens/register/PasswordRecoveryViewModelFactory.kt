package com.itsm.edutec.ui.theme.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itsm.edutec.ui.theme.api.ApiService

class PasswordRecoveryViewModelFactory(private val apiService: ApiService) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PasswordRecoveryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PasswordRecoveryViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}