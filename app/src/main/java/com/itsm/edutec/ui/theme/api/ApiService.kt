package com.itsm.edutec.ui.theme.api

import com.itsm.edutec.ui.theme.components.LoginRequest
import com.itsm.edutec.ui.theme.components.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

}