package com.example.bstuapp.api

data class TeachersModel(
    val result: List<Teacher>
)

data class Teacher(
    val id: Int,
    val name: String,
    val email: String
)
