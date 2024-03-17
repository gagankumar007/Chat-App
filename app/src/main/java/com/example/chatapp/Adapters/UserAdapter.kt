package com.example.chatapp.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.Activites.DetailChatActivity
import com.example.chatapp.R
import com.example.chatapp.databinding.UserLayoutBinding
import com.example.chatapp.models.User
import com.google.firebase.auth.FirebaseAuth

class UserAdapter(val context: Context, val userList: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(val binding: UserLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            UserLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.binding.txtName.text = userList[position].name
        holder.binding.txtEmail.text=userList[position].email

        holder.itemView.setOnClickListener(){
            val intent=Intent(context,DetailChatActivity::class.java)
            intent.putExtra("name", userList[position].name)
            intent.putExtra("uid",userList[position].uid)
            context.startActivity(intent)
        }
    }
}