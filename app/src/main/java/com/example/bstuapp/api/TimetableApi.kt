package com.example.bstuapp.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TimetableApi {
    @GET("api/timetable/v2/{type}/{id}")
    suspend fun getTimetable(
        @Path("type") type: String,
        @Path("id") id: Int,
        @Query("week") week: Int
    ): TimetableModel
}