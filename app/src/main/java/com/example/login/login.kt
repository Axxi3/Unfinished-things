package com.example.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


@Suppress("DEPRECATION")
class login : AppCompatActivity() {
private lateinit var auth :FirebaseAuth
    private lateinit var  googlesigning: GoogleSignInClient
    private lateinit var animation : LottieAnimationView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        com.example.login.databinding.ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_login)
        auth=FirebaseAuth.getInstance()

        if (auth.currentUser!=null) {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }





        val button = findViewById<Button>(R.id.button)
animation= findViewById(R.id.lottie)
       Handler(Looper.getMainLooper()).postDelayed({
               animation.visibility=View.VISIBLE
              animation.playAnimation()
           animation.loop(true)
       }, 20)

       val anno =findViewById<Button>(R.id.button3)
        anno.setOnClickListener {
            auth.signInAnonymously()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Please try agian", Toast.LENGTH_SHORT).show()
                        // If sign in fails, display a message to the user.

                    }
                }
        }

        val gso =GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail().build()
        googlesigning = GoogleSignIn.getClient(this,gso)
        button.setOnClickListener {
            signin()


        }

    }

    @SuppressLint("SuspiciousIndentation")
    private fun signin() {
      val signinIntent :Intent =googlesigning.signInIntent
        launcher.launch(signinIntent)
    }
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result ->
        if (result.resultCode== Activity.RESULT_OK) {
                val task =GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
if (task.isSuccessful) {
val account :GoogleSignInAccount ? =task.result
    if (account!=null) {
updateUI(account)
    }
}
        else {

    Toast.makeText(this, "Please try again", Toast.LENGTH_SHORT).show()}
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential: AuthCredential = GoogleAuthProvider.getCredential(account.idToken,null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if ( it.isSuccessful) {

                startActivity(Intent(this, setup_222::class.java))
            }
            else {

                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()}
        }

    }
}