package com.itsm.edutec.ui.theme.screens.profiles.teacher.content

import com.google.gson.annotations.SerializedName

data class MaterialRequest (
    @SerializedName("fileName")
    val fileName: String,

    @SerializedName("fileUrl")
    val fileUrl: String,

    @SerializedName("fileType")
    val fileType: String
)