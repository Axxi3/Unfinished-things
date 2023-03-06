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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var discoverAdapter: DiscoverAdapter2
    private lateinit var searchRecycle2: RecyclerView
    private lateinit var cardView: CardView
    private lateinit var searcher: SearchView
    private var time = 0L

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val home = Real_home()
        val profile = Real_profile()
        var bot = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bot.setOnItemSelectedListener { item ->
            var fragment: Fragment? = null
            when (item.itemId) {
                R.id.profile -> replace(profile)
                R.id.manga -> toaster()
                else -> replace(home)
            }
        }
        searchRecycle2 = findViewById(R.id.searchRecycle2)
        cardView = findViewById(R.id.shower)
        searcher = findViewById(R.id.Searcher)
        searcher.isIconifiedByDefault = false
        searcher.queryHint = "Looking for something underrrated?"
        searcher.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                // cardView.visibility=View.GONE
                return true
            }
        })


    }

    private fun toaster(): Boolean {
        Toast.makeText(this, "Comming Soon", Toast.LENGTH_SHORT).show()
        return false

    }

    private fun filterList(query: String?) {
        cardView.visibility = View.VISIBLE
        searchRecycle2.visibility = View.VISIBLE


        val search = getsearch.datains3.getSearch(query)
        search.enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                Log.d("search", "onResponse: " + response)
                if (query != null) {
                    if (query.isNotEmpty()) {
                        val searchyy = response?.body()
                        if (searchyy != null) {
                            discoverAdapter =
                                DiscoverAdapter2(null, this@MainActivity, searchyy.results!!)
                            searchRecycle2.adapter = discoverAdapter
                            val layouttManager =
                                LinearLayoutManager(
                                    this@MainActivity,
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




