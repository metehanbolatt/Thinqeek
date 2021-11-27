package com.metehanbolat.thinqeek.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.metehanbolat.thinqeek.adapter.ChatUserRecyclerAdapter
import com.metehanbolat.thinqeek.model.ChatUser

class ChatUserFragmentViewModel : ViewModel() {

    fun getUserComment(isWhat: String, contentTitle: String, firestore: FirebaseFirestore, chatList: ArrayList<ChatUser>, chatAdapter: ChatUserRecyclerAdapter){
        firestore.collection(isWhat).document(contentTitle).collection("userComments").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if (error == null){
                if (value != null){
                    if (!value.isEmpty){
                        val chats = value.documents
                        chatList.clear()
                        for (chat in chats){
                            val userChat = ChatUser(
                                userDisplayName = chat["userDisplayName"].toString(),
                                comment = chat["userComment"].toString(),
                                date = chat["date"].toString()
                            )
                            chatList.add(userChat)
                        }
                        chatAdapter.notifyDataSetChanged()
                    }
                }
            }
        }

    }
}