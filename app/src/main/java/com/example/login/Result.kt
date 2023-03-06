package com.example.login

data class Result(
    val id: String,
    val title: String,
    val poster_path: String,
    val popularity: String,
    val overview:String,
    val backdrop_path :String,
    val vote_average:String,
    val release_date:String,
) :java.io.Serializable{
}
