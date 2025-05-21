package com.itsm.edutec.ui.theme.screens.profiles.student

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsm.edutec.ui.theme.api.ApiClient.apiService
import com.itsm.edutec.ui.theme.api.EnrollRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EnrollViewModel : ViewModel() {
    private val _enrollmentStatus = MutableStateFlow<String?>(null)
    val enrollmentStatus: StateFlow<String?> get() = _enrollmentStatus

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    fun enroll(courseId: String, studentEmail: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _enrollmentStatus.value = null
            try {
                val response = apiService.enrollCourse(EnrollRequest(courseId, studentEmail))
                if (response.isSuccessful) {
                    _enrollmentStatus.value = response.body()?.message ?: "Inscripción exitosa"
                } else {
                    _enrollmentStatus.value = "Error al inscribirse"
                }
            } catch (e: Exception) {
                _enrollmentStatus.value = "Error de conexión"
            } finally {
                _isLoading.value = false
            }
        }
    }
}