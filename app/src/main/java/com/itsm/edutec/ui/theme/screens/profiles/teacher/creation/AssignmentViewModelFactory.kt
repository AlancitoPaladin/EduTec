package com.itsm.edutec.ui.theme.screens.profiles.teacher.creation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itsm.edutec.ui.theme.session.SessionManager

@Suppress("UNCHECKED_CAST")
class AssignmentViewModelFactory(
    private val sessionManager: SessionManager
    ): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AssignmentViewModel::class.java) -> {
                AssignmentViewModel(sessionManager) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}