package com.example.chatapp.LogInSignUp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chatapp.R

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_sign_up)
    }
}