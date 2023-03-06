package com.example.login

data class reviewdata(
    val id: Int,
    val page: Int,
    val results: List<ResultX>,
    val total_pages: Int,
    val total_results: Int
)