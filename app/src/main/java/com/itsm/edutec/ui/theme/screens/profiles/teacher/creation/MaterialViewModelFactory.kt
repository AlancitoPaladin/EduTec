package com.itsm.edutec.ui.theme.screens.profiles.teacher.creation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MaterialViewModelFactory(
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MaterialViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MaterialViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}