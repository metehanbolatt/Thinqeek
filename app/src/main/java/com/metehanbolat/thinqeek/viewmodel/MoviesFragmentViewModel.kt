package com.metehanbolat.thinqeek.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.metehanbolat.thinqeek.adapter.MoviesRecyclerAdapter
import com.metehanbolat.thinqeek.model.Movies

class MoviesFragmentViewModel : ViewModel() {

    var isClickable = MutableLiveData(false)

    fun getFilm(query: String, firestore: FirebaseFirestore, movieList: ArrayList<Movies>, adapter: MoviesRecyclerAdapter){
        firestore.collection("Movies").orderBy(query, Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if (error == null){
                if (value != null){
                    if (!value.isEmpty){
                        val movies = value.documents
                        movieList.clear()
                        for (movie in movies){
                            val myMovie = Movies(
                                director = movie["director"].toString(),
                                name = movie["name"].toString(),
                                year = movie["year"].toString().toInt(),
                                comment = movie["comment"].toString(),
                                rate = movie["rate"].toString().toDouble(),
                                downloadUrl = movie["downloadUrl"].toString(),
                                date = movie["date"].toString(),
                                subject = movie["subject"].toString(),
                                type = movie["type"].toString(),
                                stars = movie["stars"].toString(),
                                time = movie["time"].toString(),
                                author = movie["author"].toString()
                            )
                            movieList.add(myMovie)
                        }
                        adapter.notifyDataSetChanged()
                        isClickable.value = true
                    }
                }
            }
        }
    }
}