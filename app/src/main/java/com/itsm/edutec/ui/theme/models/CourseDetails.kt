package com.itsm.edutec.ui.theme.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsm.edutec.ui.theme.api.ApiClient.apiService
import com.itsm.edutec.ui.theme.api.Course
import kotlinx.coroutines.launch

class CourseDetails : ViewModel() {

    private val _course = MutableLiveData<Course?>()
    val course: LiveData<Course?> get() = _course
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun fetchCourseDetails(courseId: String?) {
        if (courseId.isNullOrEmpty()) {
            _errorMessage.postValue("Error: El ID del curso es inválido")  // postValue para evitar error de hilo
            return
        }

        viewModelScope.launch {
            _isLoading.postValue(true)  // postValue para manejar hilos correctamente
            try {
                val response = apiService.getCourseDetails(courseId)
                if (response.isSuccessful) {
                    _course.postValue(response.body())
                } else {
                    _errorMessage.postValue("Error: ${response.message()}")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Exception: ${e.message}")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}

