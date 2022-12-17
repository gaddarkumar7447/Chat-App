package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.chatapp.ChatActivity.Chat
import com.example.chatapp.LogInSignUp.LogIn
import com.example.chatapp.LogInSignUp.SignIn

class SplaceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splace)
        supportActionBar?.hide()
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LogIn::class.java))
            finish()
        }, 2000)
    }
}