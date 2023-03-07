package com.example.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.login.Tmdb.moviesIns
import com.example.login.repo.PagingAdapter
import com.example.login.repo.pagingViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.components.SingletonComponent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*
import javax.inject.Singleton

@AndroidEntryPoint
class Real_home : Fragment() {
    private lateinit var adapter1: Adapter
    private lateinit var popularadapter: Adapter
    private lateinit var DiscoveryAdapter: DiscoverAdapter2
    private var pager: Int = 2
    private lateinit var  Adapter: PagingAdapter
   // private  var pagingViewModel: pagingViewModel
    private  lateinit var discovery:RecyclerView
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


    @SuppressLint("ResourceAsColor", "FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        discovery=view.findViewById(R.id.Discovery)


        discoverdata(pager)

    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun discoverdata(pager: Int) {
        Adapter= PagingAdapter(this)
        val pagingViewModel:pagingViewModel=ViewModelProvider(this).get(pagingViewModel::class.java)
        discovery.layoutManager=LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        discovery.setHasFixedSize(true)
        discovery.adapter=Adapter
        pagingViewModel.list.observe(this@Real_home, androidx.lifecycle.Observer {
            Adapter.submitData(lifecycle,it)

        })

    }


    @SuppressLint("SuspiciousIndentation")
    private fun getData() {
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
@InstallIn(SingletonComponent::class)
@Module
class Network{
    @Singleton
    @Provides
    fun getRetro() :Retrofit{
        return Retrofit.Builder().baseUrl(Base_url).addConverterFactory(GsonConverterFactory.create()).build()
    }
    @Singleton
    @Provides
    fun getdiscy(retrofit: Retrofit):discer2{
        return retrofit.create(discer2::class.java)
    }
}


