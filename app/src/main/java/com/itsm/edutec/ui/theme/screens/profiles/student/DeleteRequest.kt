package com.itsm.edutec.ui.theme.screens.profiles.student

import com.google.gson.annotations.SerializedName

data class DeleteRequest (
    @SerializedName("_id")
    val id: String,

    @SerializedName("userEmail")
    val userEmail: String

)