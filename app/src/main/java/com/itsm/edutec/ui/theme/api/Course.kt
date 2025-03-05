package com.itsm.edutec.ui.theme.api

import com.google.gson.annotations.SerializedName

data class Course(
    @SerializedName("_id")
    val id: String,

    @SerializedName("course")
    val course: String,

    @SerializedName("start")
    val start: String,

    @SerializedName("end")
    val end: String,

    @SerializedName("year")
    val year: Int,

    @SerializedName("teacherEmail")
    val teacherEmail: String,

    @SerializedName("image")
    val image: String,

    @SerializedName("stars")
    val stars: Double,

    @SerializedName("category")
    val category: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("level")
    val level: String
)