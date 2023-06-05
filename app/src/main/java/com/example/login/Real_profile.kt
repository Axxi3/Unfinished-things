package com.example.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.login.profiledataobj.profiledatains
import com.example.login.repo.ProfileData
import com.example.login.repo.TokenManager
import com.example.login.repo.requestToken
import com.example.login.seeobj.seeins
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_real_profile.*
import kotlinx.android.synthetic.main.fragment_real_profile.Realname
import kotlinx.android.synthetic.main.fragment_real_profile.Realpic
import kotlinx.android.synthetic.main.fragment_real_profile.login
import retrofit2.*

class Real_profile : Fragment() {
    lateinit var auth: FirebaseAuth
    private lateinit var Wishrecycler:Adapter
    lateinit var name: TextView
    private lateinit var sharedPreferences: SharedPreferences
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

    @SuppressLint("SuspiciousIndentation", "UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = activity!!.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        var sessionId = sharedPreferences?.getString("sessionId", null)

        if(sessionId==null) {
            textView9.visibility=View.VISIBLE
                    textView9.text="Signin with your tmdb account"
            Toast.makeText(
                activity,
                "Please login from tmdb id so that i can store your data",
                Toast.LENGTH_SHORT
            ).show()
            login.setOnClickListener {
                if (username != null && password != null) {
                    TokenManager.getToken { token ->
                        if (token != null) {
                            val credentials = logindata.Credentials(
                                username.text.toString(),
                                password.text.toString(),
                                token
                            )
                            loginobj.loginins.login(credentials)
                                .enqueue(object : Callback<requestToken> {
                                    override fun onResponse(
                                        call: Call<requestToken>,
                                        response: Response<requestToken>
                                    ) {
                                        val token2 = response.body()?.request_token
                                        Log.d("request", "onResponse: $token2")
                                        TokenManager.getsession { session ->
                                            session?.let {
                                                with(sharedPreferences?.edit()) {
                                                    this?.putString("sessionId", session)
                                                    this?.apply()
                                                }
                                            }
                                        }
                                    }

                                    override fun onFailure(call: Call<requestToken>, t: Throwable) {
                                        Log.d("request", "onResponse: $t")
                                    }
                                })

                        } else {
                            // Token is not available, handle error here
                        }
                    }
                } else {
                    Toast.makeText(activity, "Please Fill the details", Toast.LENGTH_SHORT).show()
                }

            }
        }  else {
            username.visibility=View.GONE
            password.visibility=View.GONE
            login.visibility=View.GONE
            textView11.visibility=View.GONE
            textView9.visibility=View.VISIBLE
            GridRecycle.visibility=View.VISIBLE
                   showdata(sessionId)

        }

       sessionId = sharedPreferences?.getString("sessionId", null)
        Log.d("prefrence", "onViewCreated: $sessionId")
        sessionId?.let { showdata(it) }
        name = view.findViewById(R.id.Realname)
        view.findViewById<Button>(R.id.button2).setOnClickListener {
            auth.signOut()
            logout()

        }



        textView11.setOnClickListener {
            val a:Intent= Intent(Intent.ACTION_VIEW,Uri.parse("https://www.themoviedb.org/signup"))
                startActivity(a)
        }
    }

    private fun showrecycle(id: String) {
        var sessionId = sharedPreferences?.getString("sessionId", null)
                  val wishins=seeins.getsee(id, sessionId.toString())
        wishins.enqueue(object:Callback<Movie>{
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                val data=response.body()
                if(data?.results!=null) {
                    Wishrecycler = Adapter(null,null,this@Real_profile, data.results,"movie")
                    if(Wishrecycler!=null) GridRecycle.adapter=Wishrecycler

                    val layoutManager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
                    GridRecycle.layoutManager = layoutManager
                }
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }


    private fun showdata(session: String) {
        var id:String
        Log.d("sessionid", "showdata: $session")
        val ins = profiledatains.gettoken(session)
        ins.enqueue(object : Callback<ProfileData> {
            override fun onResponse(call: Call<ProfileData>, response: Response<ProfileData>) {
                val data = response.body()
               val editor=sharedPreferences.edit()
                editor.putString("id",data?.id.toString()).commit()
                Realname.visibility = View.VISIBLE
                id=data?.id.toString()
                if (data?.name != null) {
                    Realname.text = data.name
                }
                else {
                    Realname.text = data!!.username
                }
                if (data!!.avatar.tmdb.avatar_path!=null )  {
                    Glide.with(this@Real_profile).load("https://image.tmdb.org/t/p/w500"+data.avatar.tmdb.avatar_path).into(Realpic)
                }
                showrecycle(id)
            }

            override fun onFailure(call: Call<ProfileData>, t: Throwable) {
                Log.d("sessioon", "onFailure: $t")
            }
        })

    }

    @SuppressLint("SuspiciousIndentation")
    private fun logout() {
        Toast.makeText(activity, "You won't be missed", Toast.LENGTH_SHORT).show()
        val intent: Intent = Intent(activity, login::class.java)
        startActivity(intent)
    }


}
