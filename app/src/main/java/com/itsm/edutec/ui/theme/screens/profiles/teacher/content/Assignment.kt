package com.itsm.edutec.ui.theme.screens.profiles.teacher.content

import com.google.gson.annotations.SerializedName

data class Assignment(
    @SerializedName("_id")
    val id: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("dueDate")
    val dueDate: String
)