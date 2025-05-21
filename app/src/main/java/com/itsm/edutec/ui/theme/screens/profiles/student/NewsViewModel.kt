package com.itsm.edutec.ui.theme.screens.profiles.student

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsm.edutec.ui.theme.api.ApiClient.apiService
import com.itsm.edutec.ui.theme.api.CoursePreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    private val _courses = MutableStateFlow<List<CoursePreview>>(emptyList())
    val courses: StateFlow<List<CoursePreview>> get() = _courses

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun fetchCourses(page: Int = 1, perPage: Int = 10) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val response = apiService.getNews(page, perPage)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _courses.value = it.courses
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