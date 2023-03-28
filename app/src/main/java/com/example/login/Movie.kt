package com.example.login

import com.google.gson.annotations.SerializedName

class Movie(
    @SerializedName("page")
    val page:Int,
            @SerializedName("results")
            val results:List<Result>?,
    @SerializedName("total_page")
            val total_pages:Int,
            val total_results:Int) {
    data class Result(
        var profile_path:String?,
        val id: String,
        val name:String,
        val title: String,
        val poster_path: String,
        val popularity: String,
        val overview:String,
        val backdrop_path :String,
        val vote_average:String,
        val release_date:String,
    ) :java.io.Serializable{
    }

}