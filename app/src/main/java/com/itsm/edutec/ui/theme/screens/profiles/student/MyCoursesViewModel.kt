package com.itsm.edutec.ui.theme.screens.profiles.student

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsm.edutec.ui.theme.api.ApiClient.apiService
import com.itsm.edutec.ui.theme.api.CoursePreview
import com.itsm.edutec.ui.theme.models.UserRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MyCoursesViewModel : ViewModel() {

    private val _myCourses = MutableStateFlow<List<CoursePreview>>(emptyList())
    val myCourses: StateFlow<List<CoursePreview>> = _myCourses

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchStudentCourses(email: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apiService.getCoursesByStudent(UserRequest(email))
                if (response.isSuccessful) {
                    response.body()?.let {
                        _myCourses.value = it
                    }
                } else {
                    _error.value = "Error al obtener los cursos"
                }
            } catch (e: Exception) {
                _error.value = "Error de conexión"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
