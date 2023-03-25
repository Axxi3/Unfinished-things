package com.example.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.login.hellocast.castins
import kotlinx.android.synthetic.main.activity_cast_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class castShower : AppCompatActivity() {
    private lateinit var castDetailsAdapter: castDetailsAdapter
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cast_details)
        val extras = intent.extras
        var CAST_ID=""
        if(extras!=null){
        CAST_ID=extras.getString("cast_id","")}
        showcast(CAST_ID)

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