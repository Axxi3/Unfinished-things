package com.example.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint

class Real_profile : Fragment() {
lateinit var auth: FirebaseAuth
lateinit var database: DatabaseReference
lateinit var name:TextView
lateinit var profilepic:ImageView
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val auth = FirebaseAuth.getInstance()
        readdata()
        profilepic=view.findViewById(R.id.Realpic)
        val helllo=  Glide.with(this).load("https://api.lorem.space/image/album?w=150&h=150").error(R.drawable.samurai)
            .into(profilepic)


        name=view.findViewById(R.id.Realname)
        view.findViewById<Button>(R.id.button2).setOnClickListener {
            auth.signOut()
                 logout()

        }
    }

    private fun readdata() {

        val later = FirebaseAuth.getInstance().currentUser?.uid.toString()
        database=FirebaseDatabase.getInstance("https://real-cc801-default-rtdb.firebaseio.com/").getReference("User")
        database.child(later).get().addOnSuccessListener {
            name.text=it.child("uid").value.toString()
            Log.d("profile", "readdata: " + it.child("uid").value.toString())
        }.addOnFailureListener {
            Log.d("profile", "readdata: Something Went Wrong")
           // Toast.makeText(this@Real_profile, "Something Went Wrong", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun logout() {
     val intent:Intent = Intent(activity,login::class.java)
        startActivity(intent)
    }


}