package com.itsm.edutec.ui.theme.api

import com.itsm.edutec.ui.theme.models.TeacherRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface CourseService {
    @POST("get_courses_by_teacher")
    suspend fun getCoursesByTeacher(@Body request: TeacherRequest): List<Course>
}