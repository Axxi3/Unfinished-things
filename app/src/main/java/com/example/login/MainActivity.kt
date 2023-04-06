package com.example.login

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


class MainActivity2 : AppCompatActivity() {
    private lateinit var discoverAdapter: DiscoverAdapter2
    private lateinit var searchRecycle2: RecyclerView
    private lateinit var cardView: CardView
    private lateinit var searcher: SearchView
    private var time = 0L
    private lateinit var main:FragmentContainerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val home = Real_home()
        val profile = Real_profile()
        val news=Real_news()
        val bot = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bot.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.profile -> replace(profile)
                R.id.manga -> replace(news)
                else -> replace(home)
            }
        }
        main = findViewById(R.id.fragmentContainerView)
        searchRecycle2 = findViewById(R.id.searchRecycle2)
        cardView = findViewById(R.id.shower)
        searcher = findViewById(R.id.Searcher)
        searcher.isIconifiedByDefault = false
        searcher.queryHint = "Looking for something underrrated?"
        searcher.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Handler().postDelayed({
                    val inputMethodManager =
                        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(cardView.windowToken, 0)
                }, 300)
                return false
            }

            override  fun onQueryTextChange(newText: String?): Boolean {
                if(newText!=null) {

            CoroutineScope(Dispatchers.IO).async{
                filterList(newText)
            }
                 }
                else {
                    main.visibility=View.VISIBLE
                    cardView.visibility = View.GONE
                    searchRecycle2.visibility = View.GONE
                    Handler().postDelayed({
                        val inputMethodManager =
                            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(cardView.windowToken, 0)
                    }, 300)
                }
                // cardView.visibility=View.GONE
                return true
            }
        })


    }


    private fun filterList(query: String?) {
        Log.d("search", "readdata: "+ Thread.currentThread().name)
        cardView.visibility = View.VISIBLE
        var what:String="multi"




        val search = getsearch.datains3.getSearch("search",what,query)

        search.enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                searchRecycle2.visibility = View.VISIBLE
                Log.d("search", "onResponse: " + response)
                if (query != null) {
                    if (query.isNotEmpty()) {
                        val searchyy = response?.body()
                        if (searchyy != null) {
                            discoverAdapter =
                                DiscoverAdapter2(null, this@MainActivity2, searchyy.results!!)
                            searchRecycle2.adapter = discoverAdapter
                            val layouttManager =
                                LinearLayoutManager(
                                    this@MainActivity2,
                                    LinearLayoutManager.VERTICAL,
                                    false
                                )
                            searchRecycle2.layoutManager = layouttManager
                        }
                    } else {
                        cardView.visibility = View.GONE
                        searchRecycle2.visibility = View.GONE
                        Handler().postDelayed({
                            val inputMethodManager =
                                getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                            inputMethodManager.hideSoftInputFromWindow(cardView.windowToken, 0)
                        }, 300)
                    }
                }

            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Log.d("search", "onFailure:Something went wrong ")
            }


        })


    }


    fun replace(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment)
            .commit()
        return true
    }

    override fun onBackPressed() {
        if (time + 2000 > System.currentTimeMillis()) {
            onDestroy()
            super.onBackPressed()

        } else {
            Toast.makeText(this, "Press again to Exit", Toast.LENGTH_SHORT).show()
        }
        time = System.currentTimeMillis()
    }
}


interface search{
    //https://api.themoviedb.org/3/search/movie?api_key=b12e3fdf95940ab558f054895f4b79bb&language=en-US&query=jungle
    //https://api.themoviedb.org/3/person/52fe49699251416c910ac665?api_key=b12e3fdf95940ab558f054895f4b79bb&page=1&include_adult=false
    @GET("3/{search}/{multi}?api_key=$API&page=1&include_adult=true&append_to_response=videos")
    fun getSearch(@Path("search")search:String,@Path("multi")multi:String,@Query("query") query: String?):Call<Movie>
}
object getsearch{
    val datains3:search
    init {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
        datains3 = retrofit.create(search::class.java)
    }
}




