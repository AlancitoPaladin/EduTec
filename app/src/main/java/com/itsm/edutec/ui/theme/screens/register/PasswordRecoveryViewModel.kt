package com.itsm.edutec.ui.theme.screens.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsm.edutec.ui.theme.api.ApiService
import kotlinx.coroutines.launch

class PasswordRecoveryViewModel(private val apiService: ApiService) : ViewModel() {
    var uiState by mutableStateOf<UiState>(UiState.Idle)
        private set

    fun recoverPassword(email: String) {
        viewModelScope.launch {
            uiState = UiState.Loading
            uiState = try {
                val response = apiService.recoverPassword(PasswordRecoveryRequest(email))
                if (response.isSuccessful) {
                    UiState.Success("Correo de recuperación enviado.")
                } else {
                    UiState.Error("Error al recuperar: ${response.message()}")
                }
            } catch (e: Exception) {
                UiState.Error("Error de conexión: ${e.localizedMessage}")
            }
        }
    }
}