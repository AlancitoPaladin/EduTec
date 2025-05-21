package com.itsm.edutec.ui.theme.screens.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsm.edutec.ui.theme.api.ApiService
import kotlinx.coroutines.launch

class RegisterViewModel(private val apiService: ApiService) : ViewModel() {

    var uiState by mutableStateOf<UiState>(UiState.Idle)
        private set

    fun registerUser(request: RegisterRequest) {
        viewModelScope.launch {
            uiState = UiState.Loading
            uiState = try {
                val response = apiService.registerUser(request)
                if (response.isSuccessful) {
                    UiState.Success("Usuario registrado correctamente.")
                } else {
                    UiState.Error("Error al registrar: ${response.message()}")
                }
            } catch (e: Exception) {
                UiState.Error("Error de conexión: ${e.localizedMessage}")
            }
        }
    }
}