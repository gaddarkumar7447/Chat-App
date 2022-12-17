package com.example.chatapp.LogInSignUp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivitySignInBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignIn : AppCompatActivity() {
    private lateinit var bindingClass : ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        supportActionBar?.hide()
        bindingClass.gotologin.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, LogIn::class.java))
        })
        firebaseAuth = FirebaseAuth.getInstance()
        bindingClass.signup.setOnClickListener(View.OnClickListener {
            val email = bindingClass.signupemail.text.toString().trim()
            val password = bindingClass.signuppassword.text.toString().trim()
            if (email.isEmpty()){
                bindingClass.signupemail.error = "Enter the email"
            }
            else if (password.length < 7){
                bindingClass.signuppassword.error = "password should greater than 7 digit"
            }
            else{
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                    OnCompleteListener { task ->
                        Log.d("Tag", "Work")
                        if (task.isSuccessful){
                            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                            sendEmailVerification()
                        }
                        else{
                            Toast.makeText(this, "Fail to register", Toast.LENGTH_SHORT).show()
                        }
                    })
            }

        })
    }

    private fun sendEmailVerification() {
        val firebaseUser : FirebaseUser? = firebaseAuth.currentUser
        firebaseUser?.sendEmailVerification()?.addOnCompleteListener(OnCompleteListener {
                task ->
            if (task.isSuccessful){
                Toast.makeText(this, "Verify your email in spam",Toast.LENGTH_SHORT).show()
                firebaseAuth.signOut()
                finish()
                startActivity(Intent(this, LogIn::class.java))
            }else{
                Toast.makeText(this, "Failed to send verification email", Toast.LENGTH_SHORT).show()
            }
        })
    }
}