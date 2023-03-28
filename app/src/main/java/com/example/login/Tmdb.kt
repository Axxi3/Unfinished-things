package com.example.login

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//https://api.themoviedb.org/3/movie/popular?api_key=b12e3fdf95940ab558f054895f4b79bb&language=en-US&page=1
//https://api.themoviedb.org/3/trending/all/day?api_key=b12e3fdf95940ab558f054895f4b79bb&append_to_response=videos
const val Base_url = "https://api.themoviedb.org/"
const val API = "b12e3fdf95940ab558f054895f4b79bb"

interface Tmdb1 {
    @GET("3/{movie}/popular?api_key=$API")
    fun getMovies(@Path("movie")movie:String,@Query("page") page: Int): Call<Movie>

}


object Tmdb {
    val moviesIns: Tmdb1

    init {
        val retrofit = Retrofit.Builder().baseUrl(Base_url)
            .addConverterFactory(GsonConverterFactory.create()).build()
         moviesIns = retrofit.create(Tmdb1::class.java)

    }
}
interface TmdbforTrend {
    @GET("3/trending/{all}/{day}?api_key=$API")
    fun gettrend(@Path("all")all:String,@Path("day")day:String):Call<Movie>

}
object tmdbTrend{
    val trendins:TmdbforTrend
    init {
        val retrofit = Retrofit.Builder().baseUrl(Base_url)
            .addConverterFactory(GsonConverterFactory.create()).build()
        trendins= retrofit.create(TmdbforTrend::class.java)
    }
}
interface castDetails{
    //https://api.themoviedb.org/3/person/52fe49699251416c910ac665?api_key=b12e3fdf95940ab558f054895f4b79bb&language=en-US
    @GET("3/person/{id}?api_key=$API&language=en-US")
    fun getCast(@Path("id")id:String):Call<castdetailsdata>
}
object hellocast{
    val castins:castDetails
    init {
        val retrofit = Retrofit.Builder().baseUrl(Base_url)
            .addConverterFactory(GsonConverterFactory.create()).build()
        castins = retrofit.create(castDetails::class.java)
    }
}