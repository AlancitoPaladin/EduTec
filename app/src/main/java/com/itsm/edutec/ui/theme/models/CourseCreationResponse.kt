package com.itsm.edutec.ui.theme.models

import com.itsm.edutec.ui.theme.api.Course

data class CourseCreationResponse(
    val message: String,
    val course: Course
)