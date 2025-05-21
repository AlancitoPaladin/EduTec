package com.itsm.edutec.ui.theme.models

import com.itsm.edutec.ui.theme.screens.profiles.teacher.content.CourseContentResponse

sealed class CourseContentState {
    object Loading : CourseContentState()
    data class Success(val content: CourseContentResponse) : CourseContentState()
    data class Error(val message: String) : CourseContentState()
}
