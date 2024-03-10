package com.example.bstuapp.api

import retrofit2.http.GET
import retrofit2.http.Query

interface GroupsApi {
        @GET("api/timetable/search")
        suspend fun getAllGroups(
            @Query("search") search: String
        ): GroupsModel
}