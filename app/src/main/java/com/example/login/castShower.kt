package com.example.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.example.login.hellocast.castins
import com.example.login.repo.images
import com.example.login.seeReview.reviewins2
import kotlinx.android.synthetic.main.activity_cast_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class castShower : AppCompatActivity() {
    private lateinit var castDetailsAdapter: castDetailsAdapter
    private lateinit var adapter: AdapterImage
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cast_details)
        val extras = intent.extras
        var CAST_ID=""
        if(extras!=null){
        CAST_ID=extras.getString("cast_id","")}
        showcast(CAST_ID)
        images(CAST_ID)

    }

    private fun images(castId: String) {
              val pics=reviewins2.getReviews(castId)
        Log.d("pics", "onResponse: "+"ho raha")
        pics.enqueue(object :Callback<images> {
            override fun onResponse(call: Call<images>, response: Response<images>) {
                Log.d("pics", "onResponse: "+response)
                val data=response.body()

                adapter=AdapterImage(this@castShower,data!!.profiles)
                    carouselRecyclerview.adapter=adapter
                    carouselRecyclerview.layoutManager=LinearLayoutManager(this@castShower,HORIZONTAL,false)
                    carouselRecyclerview.apply {
                        set3DItem(true)
                        setAlpha(true)
                        setOrientation(HORIZONTAL)
                    }
            }

            override fun onFailure(call: Call<images>, t: Throwable) {
                Log.d("pics", "onFailure: Something went Wrong   " + t)
            }
        })

    }

    private fun showcast(castId: String) {
                 val casty= castins.getCast(castId)
        casty.enqueue(object :Callback<castdetailsdata>{
            override fun onResponse(
                call: Call<castdetailsdata>,
                response: Response<castdetailsdata>
            ) {
                val data=response.body()
                Log.d("casrtyyy", "onResponse: "+response)
                castDetailsAdapter=castDetailsAdapter(this@castShower,data!!)
                castRecycle.adapter=castDetailsAdapter
                castRecycle.layoutManager=LinearLayoutManager(this@castShower)
            }

            override fun onFailure(call: Call<castdetailsdata>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}