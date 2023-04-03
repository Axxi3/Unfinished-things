package com.example.login

import com.example.login.repo.ProfileData
import com.example.login.repo.requestToken
import com.example.login.repo.token
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

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

interface profile {
    //https://api.themoviedb.org/3/authentication/token/new?api_key=b12e3fdf95940ab558f054895f4b79bb
    @GET("3/authentication/token/new?api_key=$API")
    fun getdata():Call<token>
}
object profilrobj{
    val profileins:profile
    init {
        val retrofit=Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        profileins=retrofit.create(profile::class.java)
    }
}

interface sessionid{

    //https://api.themoviedb.org/3/authentication/session/new?api_key=b12e3fdf95940ab558f054895f4b79bb
     //https://api.themoviedb.org/3/authentication/session/new?api_key=b12e3fdf95940ab558f054895f4b79bb&request_token=897c36152de5794e864d72bbc869866527e23c7e
    @POST("3/authentication/session/new?api_key=$API")
    fun gettoken(@Body request_token: String?):Call<com.example.login.repo.sessionid>

}
object sessionobj{
    val sessionins:sessionid
    init {
        val retrofit=Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        sessionins=retrofit.create(sessionid::class.java)
    }
}

interface profiledata {
    //https://api.themoviedb.org/3/account?api_key=b12e3fdf95940ab558f054895f4b79bb&session_id=439e4baabe16b3d36f220ff5461f075955646644

    @GET("3/account?api_key=b12e3fdf95940ab558f054895f4b79bb")
    fun gettoken(@Query("session_id")id:String):Call<ProfileData>
}
object profiledataobj{
    val profiledatains:profiledata
    init {
        val retrofit=Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        profiledatains =retrofit.create(profiledata::class.java)
    }
}
interface logindata {
    @POST("3/authentication/token/validate_with_login?api_key=$API")
    fun login(@Body credentials: Credentials): Call<requestToken>
    data class Credentials(
        @SerializedName("username") val username: String,
        @SerializedName("password") val password: String,
        @SerializedName("request_token") val request_token:String
    )
}

object loginobj{
    val loginins:logindata
    init {
       val retrofit= Retrofit.Builder()
            .baseUrl(Base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        loginins=retrofit.create(logindata::class.java)
    }
}

interface sessioninterface{
    //https://api.themoviedb.org/3/authentication/session/new?api_key=b12e3fdf95940ab558f054895f4b79bb&request_token=070c7ce899738e1db784a8d9666834a1cf146893
    @GET("3/authentication/session/new?api_key=$API")
    fun gettoken(@Query("request_token")request_token:String):Call<com.example.login.repo.sessionid>
}
object sessionobj2{
    val sessioninstance:sessioninterface
    init {
        val retrofit=Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        sessioninstance=retrofit.create(sessioninterface::class.java)
    }
}