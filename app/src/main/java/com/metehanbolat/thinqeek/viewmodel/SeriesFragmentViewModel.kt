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
                                series["name"].toString(),
                                series["year"].toString().toInt(),
                                series["rate"].toString().toDouble(),
                                series["season"].toString().toInt(),
                                series["downloadUrl"].toString(),
                                series["comment"].toString(),
                                series["date"].toString()
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