package com.example.login

data class reviewdata(
    val id: Int,
    val page: Int,
    val results: List<Results>,
    val total_pages: Int,
    val total_results: Int
) {

    data class Results(
        val author: String,
        val author_details: List<com.example.login.ResultX.AuthorDetails>,
        val content: String,
        val created_at: String,
        val id: String,
        val updated_at: String,
        val url: String
    )
}