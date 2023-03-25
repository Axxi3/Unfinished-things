package com.example.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.login.Tmdb.moviesIns
import com.example.login.disco.moviesIns22
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*



class Real_home : Fragment() {
    private lateinit var adapter1: Adapter
    private lateinit var popularadapter: Adapter
    private var pager: Int = 2
    private lateinit var Adapter: DiscoverAdapter2
    private lateinit var discovery: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_real_home, container, false)
    }


    @SuppressLint("ResourceAsColor", "FragmentLiveDataObserve", "SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val job = GlobalScope.launch(Dispatchers.IO) {
            getData()
        }

        job.isCancelled
        discovery = view.findViewById(R.id.Discovery)

            discoverdata2(pager)


    }

    private fun discoverdata2(pager: Int) {
               val discy= moviesIns22.getMovies(pager)
        discy.enqueue(object :Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                val searchyy=response.body()
                Adapter = DiscoverAdapter2(this@Real_home, null, searchyy?.results!!)
                discovery.adapter = Adapter
                val layouttManager =
                    LinearLayoutManager(
                        activity,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                discovery.layoutManager = layouttManager
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }



    @SuppressLint("SuspiciousIndentation")
    private suspend fun getData() {
        val job = CoroutineScope(Dispatchers.IO).async {
            Log.d("bgthread2", "readdata: "+ Thread.currentThread().name)
            val pass: String = "movie"
            val movies = moviesIns.getMovies(pass, 1)
            movies.enqueue(object : Callback<Movie> {
                override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                    val movie66 = response.body()
                    if (movie66 != null)
                        Log.d("hello", response.toString())
                    adapter1 = Adapter(this@Real_home, null, movie66!!.results!!, "movie")

                    val Lemmetry = view!!.findViewById<RecyclerView>(R.id.Lemmetry)
                    Lemmetry.adapter = adapter1
                    //    var layoutManager: Lemmetry.LayoutManager = LinearLayoutManager(context)
                    Lemmetry.layoutManager =
                        LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)


                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    Log.d("hello", "onFailure: Something went wrong")
                }

            })
            val passtv: String = "tv"

            val tv = moviesIns.getMovies(passtv, 1)
            tv.enqueue(object : Callback<Movie> {
                override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                    Log.d("tv", "onResponse: " + response.toString())
                    val tv = response.body()
                    if (tv != null) {
                        val tvRecycle = view!!.findViewById<RecyclerView>(R.id.populartv)
                        popularadapter = Adapter(this@Real_home, null, tv!!.results!!, "tv")
                        tvRecycle.adapter = popularadapter
                        tvRecycle.layoutManager =
                            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                    }
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    Log.d("tv", "onFailure:Somethhng windsfds ")

                }
            })

        }
        job.await()
    }




}


//https://api.themoviedb.org/3/discover/movie/credits?api_key=b12e3fdf95940ab558f054895f4b79bb&language=en-US
//https://api.themoviedb.org/3/discover/movie?api_key=b12e3fdf95940ab558f054895f4b79bb&language=en-US
interface discer {
    @GET("3/discover/movie?api_key=b12e3fdf95940ab558f054895f4b79bb&language=en-US")
    fun getMovies(@Query("page") page: Int?): Call<Movie>

}
object disco {
    val moviesIns22: discer

    init {
        val retrofit = Retrofit.Builder().baseUrl(Base_url)
            .addConverterFactory(GsonConverterFactory.create()).build()
        moviesIns22 = retrofit.create(discer::class.java)

    }
}
interface discer2 {
    @GET("3/discover/movie?api_key=b12e3fdf95940ab558f054895f4b79bb&language=en-US")
   suspend fun getMovies(@Query("page") page: Int):Movie

}




