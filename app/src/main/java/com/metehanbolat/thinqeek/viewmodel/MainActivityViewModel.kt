package com.metehanbolat.thinqeek.viewmodel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.metehanbolat.thinqeek.adapter.SliderAdapter
import com.metehanbolat.thinqeek.model.SliderItem
import com.metehanbolat.thinqeek.view.activities.ApplicationActivity
import com.metehanbolat.thinqeek.view.activities.MainActivity
import kotlin.math.abs

class MainActivityViewModel : ViewModel() {

    val isLoading : MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkCurrentUser(auth: FirebaseAuth, context: Context, activity: MainActivity){
        val currentUser = auth.currentUser
        if (currentUser != null){
            val intent = Intent(context, ApplicationActivity::class.java)
            activity.startActivity(intent)
            activity.finish()
        }
    }

    fun slideImageCreator(firestore: FirebaseFirestore, viewPager2: ViewPager2, sliderItems: MutableList<SliderItem>){
        firestore.collection("SlideImage").addSnapshotListener { value, error ->
            if (error == null){
                if (value != null){
                    if (!value.isEmpty){
                        val movies = value.documents
                        for (movie in movies){
                            val comingImage = SliderItem(movie["image"] as String, movie["name"] as String)
                            sliderItems.add(comingImage)
                        }
                        viewPager2.adapter = SliderAdapter(sliderItems)
                        viewPager2.clipToPadding = false
                        viewPager2.clipChildren = false
                        viewPager2.offscreenPageLimit = 3
                        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                        val compositePageTransformer = CompositePageTransformer()
                        compositePageTransformer.addTransformer(MarginPageTransformer(30))
                        compositePageTransformer.addTransformer { page, position ->
                            val r = 1 - abs(position)
                            page.scaleY = 0.56f + r * 0.25f
                        }
                        viewPager2.setPageTransformer(compositePageTransformer)
                    }
                }
            }
        }
    }

}