package com.itsm.edutec.ui.theme.models

import com.google.gson.annotations.SerializedName

data class TeacherRequest (
    @SerializedName("teacherEmail")
    val teacherEmail: String
)