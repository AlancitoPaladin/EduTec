package com.itsm.edutec.ui.theme.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    val apiService: ApiService by lazy {
        RetrofitClient.retrofit.create(ApiService::class.java)
    }
}

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:5000"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Puedes cambiar a BASIC, HEADERS o BODY según tus necesidades
    }

    // Crear el cliente de OkHttp con el interceptor
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)  // Añadir el cliente con los logs
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}