package com.metehanbolat.thinqeek.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.metehanbolat.thinqeek.R
import com.metehanbolat.thinqeek.databinding.ChatUserRecyclerRowBinding
import com.metehanbolat.thinqeek.model.ChatUser
import com.metehanbolat.thinqeek.viewmodel.ChatUserFragmentViewModel

class ChatUserRecyclerAdapter(var context: Context, var chatList : ArrayList<ChatUser>, var viewModel : ChatUserFragmentViewModel ) : RecyclerView.Adapter<ChatUserRecyclerAdapter.ChatUserViewHolder>() {
    class ChatUserViewHolder(val binding : ChatUserRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatUserViewHolder {
        val binding = ChatUserRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatUserViewHolder, position: Int) {
        when(position % 7){
            0 -> holder.binding.userDisplayName.setTextColor(ContextCompat.getColor(context, R.color.color_one))
            1 -> holder.binding.userDisplayName.setTextColor(ContextCompat.getColor(context, R.color.color_two))
            2 -> holder.binding.userDisplayName.setTextColor(ContextCompat.getColor(context, R.color.color_three))
            3 -> holder.binding.userDisplayName.setTextColor(ContextCompat.getColor(context, R.color.color_four))
            4 -> holder.binding.userDisplayName.setTextColor(ContextCompat.getColor(context, R.color.color_five))
            5 -> holder.binding.userDisplayName.setTextColor(ContextCompat.getColor(context, R.color.color_six))
            6 -> holder.binding.userDisplayName.setTextColor(ContextCompat.getColor(context, R.color.color_seven))
        }

        holder.binding.userDisplayName.text = chatList[position].userDisplayName
        holder.binding.userComment.text = chatList[position].comment
    }

    override fun getItemCount(): Int {
        return chatList.size
    }
}