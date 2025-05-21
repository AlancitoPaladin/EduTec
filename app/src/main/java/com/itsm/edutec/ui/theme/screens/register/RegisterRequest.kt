package com.itsm.edutec.ui.theme.screens.register

data class RegisterRequest(
    val name: String,
    val lastName: String,
    val email: String,
    val password: String,
    val role: String
)