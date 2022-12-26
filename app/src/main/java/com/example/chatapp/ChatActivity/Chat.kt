package com.example.chatapp.ChatActivity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.Message
import com.example.chatapp.MessageAdapter
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Chat : AppCompatActivity() {
    lateinit var dataBinding : ActivityChatBinding
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private var receiveRoom : String? = null
    private var senderRoom : String? = null
    private lateinit var mDbRef : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")

        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        senderRoom = receiverUid + senderUid
        receiveRoom = senderUid + receiverUid
        mDbRef = FirebaseDatabase.getInstance().reference

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = name

        messageList = ArrayList()
        messageAdapter = MessageAdapter(this,messageList)

        dataBinding.recyclerViewChat.layoutManager = LinearLayoutManager(this)
        dataBinding.recyclerViewChat.adapter = messageAdapter

        dataBinding.sendButton.setOnClickListener(View.OnClickListener {
            val message = dataBinding.messageWrite.text.toString()
            val messageObject = Message(message, senderUid)

            mDbRef.child("chats").child(senderRoom!!).child("messages").push().setValue(messageObject).addOnSuccessListener {
                mDbRef.child("chats").child(receiveRoom!!).child("messages").push().setValue(messageObject)
            }
            dataBinding.messageWrite.setText("")
        })

        mDbRef.child("chats").child(senderRoom!!).child("messages").addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()

                for (postSnapShot in snapshot.children){
                    val message = postSnapShot.getValue(Message::class.java)
                    messageList.add(message!!)
                }
                messageAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}