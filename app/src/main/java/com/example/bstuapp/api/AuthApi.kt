package com.example.bstuapp.api

import retrofit2.http.POST
import retrofit2.http.Body

interface AuthApi {
    @POST("api/login")
    suspend fun auth(
        @Body authRequest: AuthRequest
    ): ApiResponse
}