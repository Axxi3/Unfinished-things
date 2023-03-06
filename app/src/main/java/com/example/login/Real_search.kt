package com.example.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


class Real_search : Fragment() {
  private lateinit var discoverAdapter: DiscoverAdapter2
  private lateinit var searchRecycle:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_real_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
searchRecycle=view.findViewById(R.id.searchRecycle)
        val searchbar = view.findViewById<SearchView>(R.id.searchView)
        searchbar.queryHint="Search here...."
        searchbar.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchbar.clearFocus()
                //showResults(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
          //    showResults(newText)
                return false
            }
        })







        //searchRecycle.adapter=discoverAdapter

    }
/*
    private fun showResults(newText: String?) {
        val search=getsearch.datains3.getSearch(newText)
        search.enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                Log.d("search", "onResponse: "+response)
                val searchyy=response.body()
                discoverAdapter=DiscoverAdapter2(this@Real_search,null,searchyy!!.results)
                searchRecycle.adapter=discoverAdapter
                val layouttManager =
                    LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                searchRecycle.layoutManager=layouttManager
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Log.d("search", "onFailure:Something went wrong ")
            }


        })
    }
*/
}


//https://api.themoviedb.org/3/search/multi?api_key=b12e3fdf95940ab558f054895f4b79bb&language=en-US&query=chris%20pratt&page=1&include_adult=true

interface search{
    @GET("3/search/multi?api_key=$API&page=1&include_adult=true")
    fun getSearch(@Query("query") query: String?):Call<Movie>
}
object getsearch{
    val datains3:search
    init {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
        datains3 = retrofit.create(search::class.java)
    }
}