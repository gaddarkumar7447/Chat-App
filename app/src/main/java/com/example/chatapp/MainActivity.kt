package com.example.chatapp

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
import com.example.chatapp.LogInSignUp.SignIn


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        /*Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, SignIn::class.java))
            finish()
        }, 3000)*/
    }
}