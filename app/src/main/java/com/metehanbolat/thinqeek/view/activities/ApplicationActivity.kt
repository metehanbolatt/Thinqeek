package com.metehanbolat.thinqeek.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.metehanbolat.thinqeek.R
import com.metehanbolat.thinqeek.databinding.ActivityApplicationBinding
import com.metehanbolat.thinqeek.view.fragments.MoviesFragment
import com.metehanbolat.thinqeek.view.fragments.NewsFragment
import com.metehanbolat.thinqeek.view.fragments.SeriesFragment

class ApplicationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityApplicationBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplicationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = Firebase.auth

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController : NavController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.contentDetailsFragment ||
                destination.id == R.id.bigImageFragment ||
                destination.id == R.id.newsDetailsFragment ||
                destination.id == R.id.chatUserFragment
            ){
                binding.bottomNavigation.visibility = View.INVISIBLE
            }else{
                binding.bottomNavigation.visibility = View.VISIBLE
            }
        }
    }
}