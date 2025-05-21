package com.itsm.edutec.ui.theme.screens.profiles.teacher.content

import com.google.gson.annotations.SerializedName

data class AssignmentRequest(
    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("due_date")
    val due_date: String
)