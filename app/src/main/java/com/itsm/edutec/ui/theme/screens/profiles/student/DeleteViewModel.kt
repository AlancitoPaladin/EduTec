package com.itsm.edutec.ui.theme.screens.profiles.student

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsm.edutec.ui.theme.api.ApiClient.apiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DeleteViewModel : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun deleteCourse(id: String, email: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apiService.deleteCourseStudent(DeleteRequest(id, email))
                if (response.isSuccessful) {
                    response.body()?.let {

                    }
                } else {
                    _error.value = "Error al eliminar curso"
                }
            } catch (_: Exception) {
                _error.value = "Error de conexión"
            } finally {
                _isLoading.value = false
            }
        }
    }
}