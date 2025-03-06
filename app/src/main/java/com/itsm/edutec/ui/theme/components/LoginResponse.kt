package com.itsm.edutec.ui.theme.components

import com.itsm.edutec.ui.theme.api.User

data class LoginResponse(
    val message: String,
    val user: User
)