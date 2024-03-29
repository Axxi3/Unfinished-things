package com.example.login

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.login.accobj.accins
import com.example.login.detailsobj.detailsins
import com.example.login.getdata.datains
import com.example.login.repo.*
import com.example.login.repo.sessionid
import com.example.login.wishlistobj.wishins
import kotlinx.android.synthetic.main.activity_show_movies.*
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
const val genre_ids="genere"
class show_Movies : AppCompatActivity() {
  private lateinit var id:String
   private lateinit var backdrop:ImageView
   private lateinit var poster:ImageView
    private lateinit var title:TextView
    private lateinit var idAdapter: idAdapter
    private lateinit var releaseDate:TextView
    private lateinit var overview:TextView
    private lateinit var rating:RatingBar
    private lateinit var adapter2: castAdapter
    private lateinit var crewkaAdapter:crewAdapter
    private lateinit var SimilarRecycle:Adapter2
   private lateinit var VideoKEy:String
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
        recyle = findViewById(R.id.recyclerView2)
        Lemmetry= findViewById(R.id.SimilarRecyle)
        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val myValue = sharedPreferences.getString("sessionId", null)
        val accid=sharedPreferences.getString("id",null)
        val extras = intent.extras

        if (extras != null) {
            id= extras.getString(MOVIE_ID,"")
            ///populateDetails(extras)
            movie=extras.getString(what_type,"")

        } else {
            finish()
        }
        if(myValue!=null) {
            val accins = accins.accstate(movie, id, myValue.toString())
            accins.enqueue(object : Callback<accstate> {
                override fun onResponse(call: Call<accstate>, response: Response<accstate>) {
                    val data = response.body()
                    if(data!=null) {
                        if (data!!.watchlist) {
                            wishlist.text = "Unlist"
                        }
                    }
                }

                override fun onFailure(call: Call<accstate>, t: Throwable) {
                    Log.d("wish", "onFailure: $t")
                }
            })


        }

        if(movie=="movie") {
              Season.visibility=View.GONE
        }
        
        //for genere
     val genereins=detailsins.getdetails(movie,id)
     genereins.enqueue(object :Callback<detailsdata> {
         override fun onResponse(call: Call<detailsdata>, response: Response<detailsdata>) {
             val data=response.body()
             Log.d("showdart", "onResponse: $response")
             if (data != null) {
                 Glide.with(this@show_Movies)
                     .load("https://image.tmdb.org/t/p/w1280" + data.backdrop_path)
                     .transform(CenterCrop()).error(R.drawable.unemployed)
                     .into(backdrop)
                 Glide.with(this@show_Movies)
                     .load("https://image.tmdb.org/t/p/w342"+ data.poster_path)
                     .transform(CenterCrop()).error(R.drawable.baseline_account_circle_24)
                     .into(poster)
                 if(data.title!=null) {
                     title.text=data.title
                 }  else if (data.name!==null) {
                     title.text=data.name
                 }

                 else {
                     title.text=data.original_title
                 }
                 rating.rating=data.vote_average.toFloat()/2
                 releaseDate.text=data.release_date
                 overview.text=data.overview
                 idAdapter= idAdapter(this@show_Movies,data.genres)
                 genererecycle.adapter=idAdapter
                 genererecycle.layoutManager=LinearLayoutManager(this@show_Movies,LinearLayoutManager.HORIZONTAL,false)
                 if(data.seasons!=null) {
                      val seasonAdapter=SeasonRecycle(this@show_Movies,data.seasons)
                     SeasonRecycle.adapter=seasonAdapter
                     SeasonRecycle.layoutManager=LinearLayoutManager(this@show_Movies,LinearLayoutManager.HORIZONTAL,false)
                 }



             }

         }

         override fun onFailure(call: Call<detailsdata>, t: Throwable) {
             Log.d("showdart", "onFailure: $t")
         }
     })








val pass="credits"

        //For Credits
val Movie_details = datains.getdetails(movie,id.toInt(),pass )
        Movie_details.enqueue(object : Callback<Details2> {
            override fun onResponse(call: Call<Details2>, response: Response<Details2>) {
                val  gotdetails = response.body()
                if (gotdetails!=null) {
                    Log.d("yoyoyo", "onResponse: "+response.toString())

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

//Wishlist
        wishlist.setOnClickListener {
            Log.d("myvalue", "onCreate: $myValue")
            if(myValue!=null && wishlist.text=="Wishlist") {
                val wishins=wishins.addToWishlist(accid.toString(),myValue,wishlistinterface.movieDetails(movie,id,true))
                wishins.enqueue(object :Callback<com.example.login.repo.sessionid>{
                    override fun onResponse(call: Call<sessionid>, response: Response<sessionid>) {
                        val data=response.body()
                        if(data!!.success) {
                            Toast.makeText(this@show_Movies, "Successfully added", Toast.LENGTH_SHORT).show()
                            wishlist.text="Unlist"
                        }
                        else {
                            Toast.makeText(this@show_Movies, "Something went Wrong", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<sessionid>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })
            }
            else if(myValue!=null && wishlist.text=="Unlist") {
                val wishins=wishins.addToWishlist(accid.toString(),myValue,wishlistinterface.movieDetails(movie,id,false))
                wishins.enqueue(object :Callback<com.example.login.repo.sessionid>{
                    override fun onResponse(call: Call<sessionid>, response: Response<sessionid>) {
                        val data=response.body()
                        if(data!!.success) {
                            Toast.makeText(this@show_Movies, "Successfully removed", Toast.LENGTH_SHORT).show()
                            wishlist.text="Wishlist"
                        }
                        else {
                            Toast.makeText(this@show_Movies, "Something went Wrong", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<sessionid>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })
            }
            else {
                Toast.makeText(this@show_Movies, "Please login to add to Wishlist", Toast.LENGTH_SHORT).show()

            }

        }

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

interface review2{
    //https://api.themoviedb.org/3/person/6384/images?api_key=b12e3fdf95940ab558f054895f4b79bb&language=en-US&page=1
    @GET("3/person/{id}/images?api_key=$API")
    fun getReviews( @Path("id") id: String):Call<images>
}
object seeReview{
    val reviewins2:review2
    init {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
       reviewins2 = retrofit.create(review2::class.java)
    }
}



