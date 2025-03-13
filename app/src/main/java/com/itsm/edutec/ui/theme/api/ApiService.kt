package com.itsm.edutec.ui.theme.api

import com.itsm.edutec.ui.theme.components.LoginRequest
import com.itsm.edutec.ui.theme.components.LoginResponse
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
        @Query("per_page") perPage: Int = 10
    ): Response<CourseResponse>

    @GET("course/{id}")
    suspend fun getCourseDetails(
        @Path("id") id: String
    ): Response<Course>
}