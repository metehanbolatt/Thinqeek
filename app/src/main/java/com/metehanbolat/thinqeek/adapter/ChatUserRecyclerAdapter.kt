package com.metehanbolat.thinqeek.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.metehanbolat.thinqeek.databinding.ChatUserRecyclerRowBinding
import com.metehanbolat.thinqeek.model.ChatUser
import com.metehanbolat.thinqeek.viewmodel.ChatUserFragmentViewModel

class ChatUserRecyclerAdapter(var chatList : ArrayList<ChatUser>, var viewModel : ChatUserFragmentViewModel ) : RecyclerView.Adapter<ChatUserRecyclerAdapter.ChatUserViewHolder>() {
    class ChatUserViewHolder(val binding : ChatUserRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatUserViewHolder {
        val binding = ChatUserRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatUserViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return chatList.size
    }
}