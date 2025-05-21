package com.itsm.edutec.ui.theme.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itsm.edutec.ui.theme.api.CourseService

@Suppress("UNCHECKED_CAST")
class CourseContentViewModelFactory(
    private val service: CourseService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CourseContentViewModel(service) as T
    }
}
