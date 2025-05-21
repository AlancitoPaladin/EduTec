package com.itsm.edutec.ui.theme.screens.profiles.student

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsm.edutec.ui.theme.api.Course
import com.itsm.edutec.ui.theme.api.CourseApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ConvertModel : ViewModel() {
    private val _selectedCourse = MutableStateFlow<Course?>(null)
    val selectedCourse: StateFlow<Course?> = _selectedCourse

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchCourseById(courseId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = CourseApiClient.courseService.getCourseById(courseId)
                _selectedCourse.value = response
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Error desconocido"
            } finally {
                _isLoading.value = false
            }
        }
    }
}