package com.example.bstuapp.api

import retrofit2.http.GET
import retrofit2.http.Path

interface TeacherApi {
    @GET("api/timetable/v2/{id}/teachers")
    suspend fun getAllTeachers(
       @Path("id") id: Int
    ): TeachersModel
}