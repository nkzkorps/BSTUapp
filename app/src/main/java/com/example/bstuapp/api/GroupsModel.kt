package com.example.bstuapp.api

data class GroupsModel(
    val success: Boolean,
    val result: SearchResult
)

data class SearchResult(
    val groups: List<QueryGroup>
)

data class QueryGroup (
    val id : Int,
    val name: String
)

