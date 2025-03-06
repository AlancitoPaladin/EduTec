package com.itsm.edutec.ui.theme.components

import com.itsm.edutec.ui.theme.api.User

sealed class LoginState {
    data object Idle : LoginState()
    data object Loading : LoginState()
    data class Success(val user: User) : LoginState()
    data class Error(val message: String) : LoginState()
}