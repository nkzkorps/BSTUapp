package com.example.bstuapp.api

data class MainInfoModel(
    val success: Boolean,
    val result: MainInfo
)

data class MainInfo (
    val progress: String,
    val attestation: String
)
