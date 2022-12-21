package com.example.chatapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.LogInSignUp.LogIn
import com.example.chatapp.LogInSignUp.SignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.Objects

class MainActivity : AppCompatActivity() {
    lateinit var userList : ArrayList<User>
    lateinit var adapter: UserAdapter
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseDatabase: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance().reference
        userList = ArrayList()
        adapter = UserAdapter(this, userList)

        val userRecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.adapter = adapter
        firebaseDatabase.child("user").addValueEventListener(object : ValueEventListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (postSnap in snapshot.children){
                    val current = postSnap.getValue(User::class.java)
                    if (firebaseAuth.currentUser?.uid != current?.uid){
                        userList.add(current!!)
                    }
                }
                adapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {}

        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logOut){
            firebaseAuth.signOut()
            finish()
            startActivity(Intent(this, LogIn::class.java))
        }
        if (item.itemId == R.id.signIn){
            firebaseAuth.signOut()
            finish()
            startActivity(Intent(this, SignIn::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}