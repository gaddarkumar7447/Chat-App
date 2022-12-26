package com.example.chatapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, private val messageList : ArrayList<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val ITEM_RECEIVE = 1
    private val ITEM_SEND = 2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1){
            val receive = LayoutInflater.from(context).inflate(R.layout.receive, parent, false)
            ReceiveViewHolder(receive)
        }else{
            val send = LayoutInflater.from(context).inflate(R.layout.send, parent, false)
            SendViewHolder(send)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]

        if (holder.javaClass == SendViewHolder::class.java){
            val viewHolder = holder as SendViewHolder
            holder.sendMessage.text = currentMessage.message

        }
        else{
            val viewHolder = holder as ReceiveViewHolder
            holder.receiveMessage.text = currentMessage.message
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        return if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)) ITEM_SEND
        else ITEM_RECEIVE
    }

    class SendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val sendMessage: TextView = itemView.findViewById(R.id.messageSend)
    }

    class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val receiveMessage: TextView = itemView.findViewById(R.id.messageReceive)
    }

}