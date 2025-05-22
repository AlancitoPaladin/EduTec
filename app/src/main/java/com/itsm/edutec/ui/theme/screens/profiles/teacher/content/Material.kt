package com.itsm.edutec.ui.theme.screens.profiles.teacher.content

import com.google.gson.annotations.SerializedName

data class Material(
    @SerializedName("title")
    val title: String,

    @SerializedName("content")
    val content: String
)