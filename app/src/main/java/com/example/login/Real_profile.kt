package com.example.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.login.profiledataobj.profiledatains
import com.example.login.repo.ProfileData
import com.example.login.repo.TokenManager
import com.example.login.repo.requestToken
import com.example.login.repo.sessionid
import com.example.login.sessionobj2.sessioninstance
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_real_profile.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.*

class Real_profile : Fragment() {
    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference
    lateinit var name: TextView
    var REQUEST_CODE_AUTHENTICATION = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_real_profile, container, false)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Toast.makeText(
            activity,
            "Please login from tmdb id so that i can store your data",
            Toast.LENGTH_SHORT
        ).show()
        login.setOnClickListener {
            Toast.makeText(activity, "Approve and come back", Toast.LENGTH_SHORT).show()
            if(username!=null&&password!=null)  {
            TokenManager.getToken { token ->
                if (token != null) {
                    val credentials = logindata.Credentials(username.text.toString(), password.text.toString(),token)
                    loginobj.loginins.login(credentials).enqueue(object : Callback<requestToken> {
                        override fun onResponse(
                            call: Call<requestToken>,
                            response: Response<requestToken>
                        ) {
                            val token2 = response.body()?.request_token
                            Log.d("request", "onResponse: $token2")
                                val sessionins=sessioninstance.gettoken(token2.toString())
                            sessionins.enqueue(object :Callback<com.example.login.repo.sessionid> {
                                override fun onResponse(
                                    call: Call<sessionid>,
                                    response: Response<sessionid>
                                ) {
                                    val data=response.body()
                                    Log.d("Session", "onResponse: $response")
                                    Log.d("Session", "onResponse: ${data!!.session_id}")
                                    data?.session_id?.let { it1 -> showdata(it1) }
                                }

                                override fun onFailure(call: Call<sessionid>, t: Throwable) {
                                    Log.d("Session", "onResponse: $t")
                                }
                            })


                        }

                        override fun onFailure(call: Call<requestToken>, t: Throwable) {
                            Log.d("request", "onResponse: $t")
                        }
                    })

                }
                else {
                    // Token is not available, handle error here
                }
            }
            }
            else{
                Toast.makeText(activity, "Please Fill the details", Toast.LENGTH_SHORT).show()
            }

        }
        name = view.findViewById(R.id.Realname)
        view.findViewById<Button>(R.id.button2).setOnClickListener {
            auth.signOut()
            logout()

        }
    }


    private fun showdata(session: String) {
        Log.d("sessionid", "showdata: $session")
        val ins = profiledatains.gettoken(session)
        ins.enqueue(object : Callback<ProfileData> {
            override fun onResponse(call: Call<ProfileData>, response: Response<ProfileData>) {
                val data = response.body()
                Realname.visibility=View.VISIBLE
                if(data?.username!=null) {
                    Realname.text = data!!.username
                }
                else{
                    Realname.text = "Chamar"
                }
            }

            override fun onFailure(call: Call<ProfileData>, t: Throwable) {
                Log.d("sessioon", "onFailure: $t")
            }
        })
    }

    private suspend fun readdata() {
        withContext(Dispatchers.IO) {
            Log.d("bgthread", "readdata: " + Thread.currentThread().name)
            val later = FirebaseAuth.getInstance().currentUser?.uid.toString()
            database =
                FirebaseDatabase.getInstance("https://real-cc801-default-rtdb.firebaseio.com/")
                    .getReference("User")
            database.child(later).get().addOnSuccessListener {
                name.text = it.child("uid").value.toString()
                Log.d("profile", "readdata: " + it.child("uid").value.toString())
            }.addOnFailureListener {
                Log.d("profile", "readdata: Something Went Wrong")
                // Toast.makeText(this@Real_profile, "Something Went Wrong", Toast.LENGTH_SHORT).show()
            }
        }

    }

    @SuppressLint("SuspiciousIndentation")
    private fun logout() {
        Toast.makeText(activity, "You won't be missed", Toast.LENGTH_SHORT).show()
        val intent: Intent = Intent(activity, login::class.java)
        startActivity(intent)
    }


}