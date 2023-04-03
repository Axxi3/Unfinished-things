package com.example.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.auth.User


class setup_222 : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_setup)

        val ani = findViewById<LottieAnimationView>(R.id.ani)
        database = FirebaseDatabase.getInstance("https://real-cc801-default-rtdb.firebaseio.com/").getReference("User")

        //ANIMATION
        Handler(Looper.getMainLooper()).postDelayed({
            ani.visibility= VISIBLE
            ani.playAnimation()
            ani.loop(true)
        }, 20)
        val done= findViewById<Button>(R.id.done)
        val name = findViewById<TextView>(R.id.name)

        name.text.toString()
        val user= FirebaseAuth.getInstance().currentUser
        done.setOnClickListener {
            if(name.length()>1) {

                writeNewUser(user, name.text.toString())

            }
            else {
                Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

    }



    @SuppressLint("RestrictedApi")
    private fun writeNewUser(user: FirebaseUser?, name: String) {
        val later = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val user = User (name)


        FirebaseAuth.getInstance().currentUser?.let {
            database.child(later).setValue(user).addOnCompleteListener {
                if (it.isSuccessful) {
                    letsgo()
                }

                else {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun letsgo() {
        Toast.makeText(this, "Lets start your journey", Toast.LENGTH_SHORT).show()
        val intent = Intent (this, MainActivity2::class.java)
        startActivity (intent)

    }
}