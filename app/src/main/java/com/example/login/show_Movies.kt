package com.example.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.login.getdata.datains
import com.example.login.seeReview.reviewins
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


const val MOVIE_BACKDROP = "extra_movie_backdrop"
const val MOVIE_POSTER = "extra_movie_poster"
const val MOVIE_TITLE = "extra_movie_title"
const val MOVIE_RATING = "extra_movie_rating"
const val MOVIE_RELEASE_DATE = "extra_movie_release_date"
const val MOVIE_OVERVIEW = "extra_movie_overview"
const val MOVIE_ID= "movie_id"
const val what_type="what_type"
const val BASE_URL= "https://api.themoviedb.org/"
const val AP_I = "b12e3fdf95940ab558f054895f4b79bb"
class show_Movies : AppCompatActivity() {
  private lateinit var id:String
   private lateinit var backdrop:ImageView
   private lateinit var poster:ImageView
    private lateinit var title:TextView
    private lateinit var releaseDate:TextView
    private lateinit var overview:TextView
    private lateinit var rating:RatingBar
    private lateinit var adapter2: castAdapter
    private lateinit var crewkaAdapter:crewAdapter
    private lateinit var SimilarRecycle:Adapter2
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var movie:String
    private lateinit var recyle:RecyclerView
    private lateinit var  Lemmetry:RecyclerView
    private var page: Int = 0
    private lateinit var reviewREcycle:RecyclerView

    @SuppressLint("MissingInflatedId", "CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_movies)

        backdrop = findViewById(R.id.movie_backdrop)
        poster = findViewById(R.id.movie_poster)
         title = findViewById(R.id.movie_title)
        rating = findViewById(R.id.movie_rating)
        releaseDate = findViewById(R.id.movie_release_date)
        overview = findViewById(R.id.movie_overview)
        recyle = findViewById<RecyclerView>(R.id.recyclerView2)
        Lemmetry= findViewById(R.id.SimilarRecyle)
        reviewREcycle=findViewById(R.id.reviewRecycle)
        val extras = intent.extras

        if (extras != null) {
            id= extras.getString(MOVIE_ID,"")
            populateDetails(extras)
            movie=extras.getString(what_type,"")

        } else {
            finish()
        }
val pass="credits"

        //For Credits
val Movie_details = datains.getdetails(movie,id.toInt(),pass )
        Movie_details.enqueue(object : Callback<Details2> {
            override fun onResponse(call: Call<Details2>, response: Response<Details2>) {
                val  gotdetails = response.body()
                if (gotdetails!=null) {
                    Log.d("yoyoyo", "onResponse: "+response.toString())
                    val Stringbuild = StringBuilder()

                    //Setting the reponse to activity
                    Log.d("yoyoyo", "onResponse: "+response)

                    adapter2 = castAdapter(this@show_Movies, gotdetails!!.cast)

                    recyle.adapter=adapter2
                    recyle.layoutManager= LinearLayoutManager(this@show_Movies,
                        LinearLayoutManager.HORIZONTAL,false)

                    crewkaAdapter = crewAdapter(this@show_Movies,gotdetails!!.crew)
                    val crewRecycle= findViewById<RecyclerView>(R.id.crewkarecycle)
                    crewRecycle.adapter=crewkaAdapter
                    crewRecycle.layoutManager=LinearLayoutManager(this@show_Movies,LinearLayoutManager.HORIZONTAL,false)


                }

            }

            override fun onFailure(call: Call<Details2>, t: Throwable) {
                Log.d("yoyoyoy", "Something Went Wrong ")
            }
        })


        //For similar Movies
        val similar="similar"
        val Similar_movies = datains.getdetails(movie,id.toInt(),similar)
       Similar_movies.enqueue(object :Callback<Details2> {
           @SuppressLint("SuspiciousIndentation")
           override fun onResponse(call: Call<Details2>, response: Response<Details2>) {
              val gotSimilar = response?.body()
               Log.d("Similar", "onResponse: "+ response.toString())
               if(gotSimilar!=null) {
               SimilarRecycle=Adapter2(this@show_Movies,gotSimilar.results,movie)

               Lemmetry.adapter=SimilarRecycle
               //    var layoutManager: Lemmetry.LayoutManager = LinearLayoutManager(context)
               Lemmetry.layoutManager= LinearLayoutManager(this@show_Movies,LinearLayoutManager.HORIZONTAL,false)}

           }

           override fun onFailure(call: Call<Details2>, t: Throwable) {
               Log.d("Similar", "onFailure: Something went wrong")
           }
       })



        //for Reviews
reviews()

    }

    private fun reviews() {
        Toast.makeText(this, "reviews", Toast.LENGTH_SHORT).show()
    val reviews = reviewins.getReviews(what_type,id.toInt())
    reviews.enqueue(object : Callback<reviewdata>{
        override fun onResponse(call: Call<reviewdata>, response: Response<reviewdata>) {
            val data= response.body()
                    if(data!=null){
                Log.d("review", "onResponse: "+data.toString())
                reviewAdapter= ReviewAdapter(this@show_Movies, data!!.results)
                reviewREcycle.adapter=reviewAdapter
                reviewREcycle.layoutManager=LinearLayoutManager(this@show_Movies,LinearLayoutManager.HORIZONTAL,false)}

        }

        override fun onFailure(call: Call<reviewdata>, t: Throwable) {
            Log.d("review", "Something went wrong")
            Toast.makeText(this@show_Movies, "kuch nahi hua", Toast.LENGTH_SHORT).show()
        }
    })
}

    private fun populateDetails(extras: Bundle) {


                extras.getString(MOVIE_BACKDROP)?.let { backdropPath ->
                    Glide.with(this)
                        .load("https://image.tmdb.org/t/p/w1280$backdropPath")
                        .transform(CenterCrop())
                        .into(backdrop)
                }

            extras.getString(MOVIE_POSTER)?.let { posterPath ->
                Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w342$posterPath")
                    .transform(CenterCrop()).error(R.drawable.baseline_account_circle_24)
                    .into(poster)
            }

            Log.d("yoyobhay", "populateDetails: $id")
            title.text = extras.getString(MOVIE_TITLE, "")
            rating.rating = extras.getFloat(MOVIE_RATING, 1f) / 2
            releaseDate.text = extras.getString(MOVIE_RELEASE_DATE, "")
            overview.text = extras.getString(MOVIE_OVERVIEW, "")
        }
    }


interface Details {

    @GET("3/{movie}/{id}/{credits}?api_key=$AP_I")
    fun getdetails(@Path("movie")movie:String,@Path("id") id:Int,@Path("credits")credits:String?):Call<Details2>
    //https://api.themoviedb.org/3/movie/631842/reviews?api_key=b12e3fdf95940ab558f054895f4b79bb&language=en-US&page=1
    //https://api.themoviedb.org/3/discover/movie/credits?api_key=b12e3fdf95940ab558f054895f4b79bb&language=en-US
    //https://api.themoviedb.org/3/movie/315162/similar?api_key=b12e3fdf95940ab558f054895f4b79bb

}
object getdata{
    val datains:Details
    init {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
        datains = retrofit.create(Details::class.java)
    }
}





interface Discover{
    @GET("3/discover/movie?api_key=$AP_I&language=en-US&sort_by=popularity.desc&include_adult=true&include_video=false&with_watch_monetization_types=free")
    fun DiscoverDetails():Response<Movie>
    //https://api.themoviedb.org/3/discover/movie?api_key=b12e3fdf95940ab558f054895f4b79bb&language=en-US&sort_by=popularity.desc&include_adult=true&include_video=false&page=2&with_watch_monetization_types=free
}
object getdiscover{
    val datains2:Discover
    init {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
        datains2 = retrofit.create(Discover::class.java)
    }
}

interface review{
    //https://api.themoviedb.org/3/movie/631842/reviews?api_key=b12e3fdf95940ab558f054895f4b79bb&language=en-US&page=1
    @GET("3/{movie}/{id}/reviews?api_key=$API")
    fun getReviews(@Path("movie")movie:String,@Path("id") id:Int):Call<reviewdata>
}
object seeReview{
    val reviewins:review
    init {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
       reviewins = retrofit.create(review::class.java)
    }
}



