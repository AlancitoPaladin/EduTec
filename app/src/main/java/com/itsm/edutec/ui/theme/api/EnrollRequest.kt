package com.itsm.edutec.ui.theme.api

import com.google.gson.annotations.SerializedName

data class EnrollRequest(
    @SerializedName("courseId")
    val courseId: String,

    @SerializedName("studentEmail")
    val studentEmail: String
)