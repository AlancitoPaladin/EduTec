package com.itsm.edutec.ui.theme.screens.profiles.teacher.content

data class CourseContentResponse(
    val announcements: List<Announcement>,
    val materials: List<Material>,
    val assignments: List<Assignment>
)