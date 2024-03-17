package com.example.chatapp.Activites


import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View

import com.example.chatapp.Adapters.MessageAdapter
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityDetailChatBinding
import com.example.chatapp.models.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class DetailChatActivity : AppCompatActivity(), View.OnClickListener {
    private val binding by lazy {
        ActivityDetailChatBinding.inflate(layoutInflater)
    }

    private val mDbRef by lazy {
        FirebaseDatabase.getInstance().getReference()
    }
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    var receiverRoom: String? = null
    var senderRoom: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        initializer()


    }

    fun initializer() {
        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid
        supportActionBar?.title = name
        messageList = ArrayList()

        addData()

        messageAdapter = MessageAdapter(this, messageList)
        binding.rvChat.adapter = messageAdapter
        binding.ivSend.setOnClickListener(this)
    }

    private fun addData() {
        mDbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for (postSnapShot in snapshot.children) {

                        messageList.add(postSnapShot.getValue(Message::class.java)!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.ivSend -> {
                sendChat()

            }

        }

    }

    fun sendChat() {

        // val messageObject=Message(binding.edtMessageBox.text.toString(),senderUid )
        mDbRef.child("chats").child(senderRoom!!).child("messages").push()
            .setValue(
                Message(
                    binding.edtMessageBox.text.toString(),
                    FirebaseAuth.getInstance().currentUser?.uid
                )
            ).addOnSuccessListener {
                mDbRef.child("chats").child(receiverRoom!!).child("messages").push()
                    .setValue(
                        Message(
                            binding.edtMessageBox.text.toString(),
                            FirebaseAuth.getInstance().currentUser?.uid
                        )
                    )
                binding.edtMessageBox.setText(" ")

            }


    }
}