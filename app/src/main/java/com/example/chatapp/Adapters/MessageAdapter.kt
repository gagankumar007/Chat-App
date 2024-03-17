package com.example.chatapp.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.chatapp.databinding.ReceiveLayoutBinding
import com.example.chatapp.databinding.SendLayoutBinding
import com.example.chatapp.databinding.UserLayoutBinding
import com.example.chatapp.models.Message
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context,val messageList:ArrayList<Message>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class SentViewHolder(private val binding: SendLayoutBinding) :
        RecyclerView.ViewHolder(binding.root){
            val sentMessage=binding.txtSentMessage
        }

    class ReveiceViewHolder(private val binding:ReceiveLayoutBinding):
            RecyclerView.ViewHolder(binding.root){
                val receiveMessage=binding.txtReceiveMessage

            }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if(viewType==1){
            return MessageAdapter.ReveiceViewHolder(
                ReceiveLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
        else{
            return MessageAdapter.SentViewHolder(
                SendLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            )

        }


    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       if (holder.javaClass==SentViewHolder::class.java){
           val viewHolder= holder as SentViewHolder
        holder.sentMessage.text=messageList[position].message

       }
        else{
            val viewHolder= holder as ReveiceViewHolder
           holder.receiveMessage.text=messageList[position].message

       }
    }

    override fun getItemViewType(position: Int): Int {

        if(FirebaseAuth.getInstance().currentUser?.uid.equals(messageList[position].senderId)){
            return 2
        }
        else
            return 1
    }
}