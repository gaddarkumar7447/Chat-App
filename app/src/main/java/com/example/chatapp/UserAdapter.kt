package com.example.chatapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.createSavedStateHandle
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.ChatActivity.Chat
import com.google.firebase.auth.FirebaseAuth

class UserAdapter (val context : Context, private val userList: ArrayList<User>) : RecyclerView.Adapter<UserAdapter.ViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.textName.text = currentUser.name
        holder.itemView.setOnClickListener(View.OnClickListener {
            val intenet = Intent(context.applicationContext, Chat::class.java)
            intenet.putExtra("name", currentUser.name)
            intenet.putExtra("uid", currentUser.uid)
            context.startActivity(intenet)
        })
    }

    override fun getItemCount(): Int {
        return userList.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textName = itemView.findViewById<TextView>(R.id.name)
    }
}