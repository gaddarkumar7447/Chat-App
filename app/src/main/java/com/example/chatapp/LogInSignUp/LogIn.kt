package com.example.chatapp.LogInSignUp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.chatapp.ChatActivity.Chat
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityLogInBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LogIn : AppCompatActivity() {
    private lateinit var dataBinding : ActivityLogInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_log_in)
        dataBinding.gotoforgetpassword.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, PasswordReset::class.java))
        })
        dataBinding.gotosingup.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, SignIn::class.java))
        })
        firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            finish()
            startActivity(Intent(this, Chat::class.java))
        }
        dataBinding.login.setOnClickListener(View.OnClickListener {
            val email = dataBinding.loginemail.text.toString().trim()
            val password = dataBinding.loginpassword.text.toString().trim()
            if (email.isEmpty()){
                dataBinding.loginemail.error = "Enter email"
            }
            else if (password.isEmpty()){
                dataBinding.loginpassword.error = "Enter password"
            }
            else{
                dataBinding.progressbarinmainactivity.visibility =View.VISIBLE
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(OnCompleteListener { task ->
                        if (task.isSuccessful){
                            checkMainVerification()
                        }
                        else{
                            dataBinding.progressbarinmainactivity.visibility = View.INVISIBLE
                            dataBinding.loginemail.error = "Email not exits"
                            dataBinding.loginpassword.error = "Password not exits"
                        }
                    })
            }
        })

    }

    private fun checkMainVerification() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser!!.isEmailVerified){
            Toast.makeText(this, "LogIn successful", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, Chat::class.java))
            finish()
        }else{
            dataBinding.progressbarinmainactivity.visibility = View.INVISIBLE
            Toast.makeText(this, "Verify email first", Toast.LENGTH_SHORT).show()
            firebaseAuth.signOut()
        }

    }
}