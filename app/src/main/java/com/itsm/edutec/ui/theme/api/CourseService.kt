package com.itsm.edutec.ui.theme.api

import com.itsm.edutec.ui.theme.models.CourseCreationResponse
import com.itsm.edutec.ui.theme.models.TeacherRequest
import com.itsm.edutec.ui.theme.screens.profiles.teacher.content.AnnouncementRequest
import com.itsm.edutec.ui.theme.screens.profiles.teacher.content.AssignmentRequest
import com.itsm.edutec.ui.theme.screens.profiles.teacher.content.CourseContentResponse
import com.itsm.edutec.ui.theme.screens.profiles.teacher.content.MaterialRequest
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface CourseService {
    @POST("create_course")
    suspend fun createCourse(@Body course: Course): Response<CourseCreationResponse>

    @POST("get_courses_by_teacher")
    suspend fun getCoursesByTeacher(@Body request: TeacherRequest): List<Course>

    @GET("/courses/{course_id}")
    suspend fun getCourseById(@Path("course_id") courseId: String): Course

    @POST("/courses/{course_id}/content")
    suspend fun getCourseContent(@Path("course_id") courseId: String): Response<CourseContentResponse>

    @POST("/courses/{course_id}/announcements")
    suspend fun createAnnouncement(
        @Path("course_id") courseId: String,
        @Body announcement: AnnouncementRequest
    )

    @POST("/courses/{course_id}/assignments")
    suspend fun createAssignment(
        @Path("course_id") courseId: String,
        @Body assignment: AssignmentRequest
    )

    @POST("/courses/{course_id}/material")
    suspend fun createMaterial(
        @Path("course_id") courseId: String,
        @Body material: MaterialRequest
    )


    @Multipart
    @POST("/upload/{course_id}")
    suspend fun uploadFile(
        @Path("course_id") courseId: String,
        @Part file: MultipartBody.Part
    ): Response<UploadResponse>
}