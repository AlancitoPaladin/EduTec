package com.itsm.edutec.ui.theme.screens.profiles.student

import com.google.gson.annotations.SerializedName

data class DeleteRequest (
    @SerializedName("courseId")
    val courseId: String,

    @SerializedName("userEmail")
    val userEmail: String
)