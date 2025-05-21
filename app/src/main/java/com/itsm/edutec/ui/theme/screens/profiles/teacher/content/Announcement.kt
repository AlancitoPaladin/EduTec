package com.itsm.edutec.ui.theme.screens.profiles.teacher.content

import com.google.gson.annotations.SerializedName

data class Announcement (
    @SerializedName("_id")
    val id: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("date")
    val date: String
)