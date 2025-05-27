package com.itsm.edutec.ui.theme.api

import com.itsm.edutec.ui.theme.components.LoginRequest
import com.itsm.edutec.ui.theme.components.LoginResponse
import com.itsm.edutec.ui.theme.models.UserRequest
import com.itsm.edutec.ui.theme.screens.profiles.student.DeleteRequest
import com.itsm.edutec.ui.theme.screens.register.PasswordRecoveryRequest
import com.itsm.edutec.ui.theme.screens.register.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("courses")
    suspend fun getCourses(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 20
    ): Response<CourseResponse>

    @GET("news")
    suspend fun getNews(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10
    ): Response<CourseResponse>

    @POST("course/{id}")
    suspend fun getCourseDetails(@Path("id") courseId: String): Response<Course>

    @POST("get_courses_by_student")
    suspend fun getCoursesByStudent(@Body request: UserRequest): Response<List<CoursePreview>>

    @POST("delete_course_student")
    fun deleteCourseStudent(
        @Body request: DeleteRequest
    ): Response<Any>

    @POST("/add_course_student")
    suspend fun enrollCourse(@Body request: EnrollRequest): Response<EnrollmentResponse>

    @POST("/register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<Unit>

    @POST("/reset_password")
    suspend fun recoverPassword(@Body request: PasswordRecoveryRequest): Response<Unit>
}