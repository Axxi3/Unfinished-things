package com.example.login

import com.example.login.repo.*
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

//https://api.themoviedb.org/3/movie/popular?api_key=b12e3fdf95940ab558f054895f4b79bb&language=en-US&page=1
//https://api.themoviedb.org/3/trending/all/day?api_key=b12e3fdf95940ab558f054895f4b79bb&append_to_response=videos
const val Base_url = "https://api.themoviedb.org/"
const val API = "b12e3fdf95940ab558f054895f4b79bb"

interface moviedetails{
    @GET("3/{movie}/{popular}?api_key=$API")
    fun getdetails(@Path("movie")movie:String,@Path("popular")popular:String): Call<detailsdata>

}

object detailsobj {
    val detailsins: moviedetails

    init {
        val retrofit = Retrofit.Builder().baseUrl(Base_url)
            .addConverterFactory(GsonConverterFactory.create()).build()
        detailsins = retrofit.create(moviedetails::class.java)

    }
}
interface Tmdb1 {
    @GET("3/{movie}/{popular}?api_key=$API")
    fun getMovies(@Path("movie")movie:String,@Path("popular")popular:String): Call<Movie>

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

interface wishlistinterface{
    //https://api.themoviedb.org/3/account/18591377/watchlist?api_key=b12e3fdf95940ab558f054895f4b79bb&session_id=a223b9aa1d49c0d5aecfa875c7e97b8ad9e9896f
    @POST("3/account/{id}/watchlist?api_key=$API")
    fun addToWishlist(@Path("id")id:String,@Query("session_id")session_id:String,@Body details:movieDetails):Call<com.example.login.repo.sessionid>
    data class movieDetails(
        @SerializedName("media_type")  val media_type:String,
        @SerializedName("media_id")  val media_id:String,
        @SerializedName("watchlist")  val watchlist:Boolean
    )
}
object wishlistobj{
    val wishins:wishlistinterface
    init {
         val retrofit=Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        wishins=retrofit.create(wishlistinterface::class.java)
    }
}
interface accountsState{
    //https://api.themoviedb.org/3/movie/76600/account_states?api_key=b12e3fdf95940ab558f054895f4b79bb&session_id=a223b9aa1d49c0d5aecfa875c7e97b8ad9e9896f
    @GET("3/{movie}/{id}/account_states?api_key=$API")
    fun accstate(@Path("movie")movie: String,@Path("id")id:String,@Query("session_id")session_id:String):Call<accstate>
}
object accobj{
    val accins:accountsState
    init {
        val retrofit=Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        accins =retrofit.create(accountsState::class.java)
    }
}
interface likeinterface{
    //https://api.themoviedb.org/3/account/18591377/watchlist?api_key=b12e3fdf95940ab558f054895f4b79bb&session_id=a223b9aa1d49c0d5aecfa875c7e97b8ad9e9896f
    @POST("3/account/{id}/watchlist?api_key=$API")
    fun addToLikelist(@Path("id")id: String,@Query("session_id")session_id:String,@Body details:movieDetails):Call<com.example.login.repo.sessionid>
    data class movieDetails(
        @SerializedName("media_type")  val media_type:String,
        @SerializedName("media_id")  val media_id:String,
        @SerializedName("favorite")  val favorite:Boolean
    )
}
object likeobj{
    val likeins:likeinterface
    init {
        val retrofit=Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        likeins=retrofit.create(likeinterface::class.java)
    }
}

interface seewishlist{
    //https://api.themoviedb.org/3/account/18591377/watchlist/movies?api_key=b12e3fdf95940ab558f054895f4b79bb&language=en-US&session_id=a223b9aa1d49c0d5aecfa875c7e97b8ad9e9896f
    @GET("3/account/{id}/watchlist/movies?api_key=$API")
    fun getsee(@Path("id")id:String,@Query("session_id")session_id:String):Call<Movie>
}

object seeobj{
    val seeins:seewishlist
    init {
        val retrofit=Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
       seeins=retrofit.create(seewishlist::class.java)
    }
}

//854575d654ff4fa1aae72f9475d7532a
interface news{
    @GET("everything?q=movies&apiKey=854575d654ff4fa1aae72f9475d7532a")
    fun getNews():Call<News>
}
object seeNews{
    val newsins:news
    init {
        val retrofit=Retrofit.Builder().baseUrl("https://newsapi.org/v2/").addConverterFactory(GsonConverterFactory.create()).build()
        newsins=retrofit.create(news::class.java)
    }
}