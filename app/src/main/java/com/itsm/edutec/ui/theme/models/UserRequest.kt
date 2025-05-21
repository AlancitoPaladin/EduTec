package com.itsm.edutec.ui.theme.models

import com.google.gson.annotations.SerializedName

data class UserRequest(
    @SerializedName("userEmail")
    val userEmail: String
)