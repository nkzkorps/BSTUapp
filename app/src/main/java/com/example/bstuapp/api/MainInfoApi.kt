package com.example.bstuapp.api

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface MainInfoApi {
    @Headers("Content-Type: application/json")
    @GET("api/account/student")
    suspend fun getMainInfo(
        @Header("X-Auth-Token") authToken: String
    ): MainInfoModel
}