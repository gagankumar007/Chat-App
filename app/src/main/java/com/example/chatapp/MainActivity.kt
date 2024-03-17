package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.chatapp.Activites.HomeActivity
import com.example.chatapp.Activites.LogInActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private val mAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializer()
    }

    private fun initializer() {
        supportActionBar?.hide()
        Handler(Looper.myLooper()!!).postDelayed({
            if (mAuth.currentUser == null) {
                startActivity(Intent(this, LogInActivity::class.java))
            } else {
                startActivity(Intent(this, HomeActivity::class.java))
            }
            finish()
        }, 1000)
    }
}