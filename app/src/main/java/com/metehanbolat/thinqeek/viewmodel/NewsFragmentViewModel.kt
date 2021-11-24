package com.metehanbolat.thinqeek.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.metehanbolat.thinqeek.adapter.NewsRecyclerAdapter
import com.metehanbolat.thinqeek.model.News

class NewsFragmentViewModel : ViewModel() {

    var isClickable = MutableLiveData(false)

    fun getNews(query: String, firestore: FirebaseFirestore, newsList: ArrayList<News>, adapter: NewsRecyclerAdapter){
        firestore.collection("Technologies").orderBy(query, Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if (error == null){
                if (value != null){
                    if (!value.isEmpty){
                        val movies = value.documents
                        newsList.clear()
                        for (movie in movies){
                            val myMovie = News(
                                title = movie["title"].toString(),
                                description = movie["description"].toString(),
                                downloadUrl = movie["downloadUrl"].toString(),
                                author = movie["author"].toString(),
                                date = movie["date"].toString()
                            )
                            newsList.add(myMovie)
                        }
                        adapter.notifyDataSetChanged()
                        isClickable.value = true
                    }
                }
            }
        }
    }
}