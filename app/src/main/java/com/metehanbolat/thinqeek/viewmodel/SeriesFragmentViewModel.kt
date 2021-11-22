package com.metehanbolat.thinqeek.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.metehanbolat.thinqeek.adapter.SeriesRecyclerAdapter
import com.metehanbolat.thinqeek.model.Series

class SeriesFragmentViewModel : ViewModel() {

    var isClickable = MutableLiveData(false)

    fun getSeries(query: String, firestore: FirebaseFirestore, seriesList: ArrayList<Series>, adapter: SeriesRecyclerAdapter){
        firestore.collection("Series").orderBy(query, Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if (error == null){
                if (value != null){
                    if (!value.isEmpty){
                        val seriesDoc = value.documents
                        seriesList.clear()
                        for (series in seriesDoc){
                            val myMovie = Series(
                                director = series["producer"].toString(),
                                name = series["name"].toString(),
                                year = series["year"].toString().toInt(),
                                rate = series["rate"].toString().toDouble(),
                                season = series["season"].toString().toInt(),
                                downloadUrl = series["downloadUrl"].toString(),
                                comment = series["comment"].toString(),
                                date = series["date"].toString(),
                                time = series["time"].toString(),
                                stars = series["stars"].toString(),
                                subject = series["subject"].toString(),
                                type = series["type"].toString(),
                                author = series["author"].toString()
                            )
                            seriesList.add(myMovie)
                        }
                        adapter.notifyDataSetChanged()
                        isClickable.value = true
                    }
                }
            }
        }
    }
}