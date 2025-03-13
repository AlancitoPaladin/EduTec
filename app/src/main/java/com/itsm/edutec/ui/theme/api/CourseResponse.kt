package com.itsm.edutec.ui.theme.api

import com.google.gson.annotations.SerializedName

data class CourseResponse(
    @SerializedName("courses") val courses: List<CoursePreview>,
    @SerializedName("page") val page: Int,
    @SerializedName("per_page") val perPage: Int
)