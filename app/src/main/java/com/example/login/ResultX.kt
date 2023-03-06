package com.example.login

data class ResultX(
    val author: String,
    val author_details: List<AuthorDetails>,
    val content: String,
    val created_at: String,
    val id: String,
    val updated_at: String,
    val url: String
) {
    data class AuthorDetails(
        val avatar_path: String,
        val name: String,
        val rating: Int,
        val username: String
    )
}