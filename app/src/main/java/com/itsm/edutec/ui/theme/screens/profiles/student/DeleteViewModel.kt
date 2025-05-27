package com.itsm.edutec.ui.theme.screens.profiles.student

import androidx.lifecycle.ViewModel
import com.itsm.edutec.ui.theme.api.ApiClient.apiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DeleteViewModel : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    suspend fun deleteCourse(id: String, email: String): Boolean {
        _isLoading.value = true
        return try {
            val response = apiService.deleteCourseStudent(DeleteRequest(id, email))
            if (response.isSuccessful) {
                true
            } else {
                _error.value = "Error al eliminar curso"
                false
            }
        } catch (_: Exception) {
            _error.value = "Error de conexión"
            false
        } finally {
            _isLoading.value = false
        }
    }
}
