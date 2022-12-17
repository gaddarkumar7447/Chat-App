package com.example.chatapp.LogInSignUp

import android.content.Intent
import android.content.IntentSender.SendIntentException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityPasswordResetBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

class PasswordReset : AppCompatActivity() {
    private lateinit var dataBinding : ActivityPasswordResetBinding
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_password_reset)
        supportActionBar?.hide()
        dataBinding.gobacktologin.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, LogIn::class.java))
        })
        firebaseAuth = FirebaseAuth.getInstance()
        dataBinding.passwordrecoverbutton.setOnClickListener(View.OnClickListener {
            val email = dataBinding.forgetpassword.text.toString().trim()
            if (email.isEmpty()){
                dataBinding.forgetpassword.error = "Enter the email"
            }
            else{
                dataBinding.progressBar.visibility = View.VISIBLE
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(OnCompleteListener { task ->
                    if (task.isSuccessful){
                        Toast.makeText(this, "Reset password form the email", Toast.LENGTH_SHORT).show()
                        finish()
                        startActivity(Intent(this, LogIn::class.java))
                    }else{
                        dataBinding.progressBar.visibility = View.INVISIBLE
                        dataBinding.forgetpassword.error = "Email not exits"
                    }
                })
            }
        })

    }
}