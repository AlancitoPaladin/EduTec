package com.itsm.edutec.ui.theme.api

import com.google.gson.annotations.SerializedName

data class CoursePreview (
    @SerializedName("course")
    val course: String,

    @SerializedName("image")
    val image: String,

    @SerializedName("stars")
    val stars: Double,

    @SerializedName("description")
    val description: String
)