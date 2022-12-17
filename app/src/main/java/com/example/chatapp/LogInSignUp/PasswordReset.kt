package com.example.chatapp.LogInSignUp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chatapp.R

class PasswordReset : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_reset)
        supportActionBar?.hide()

    }
}