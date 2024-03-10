package com.example.bstuapp.api

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface UserApi {
    @Headers("Content-Type: application/json")
    @GET("api/accounts")
    suspend fun getUserdata(
        @Header("X-Auth-Token") authToken: String
    ): ApiResponse
}