package com.metehanbolat.thinqeek.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.metehanbolat.thinqeek.R
import com.metehanbolat.thinqeek.databinding.ActivityMainBinding
import com.metehanbolat.thinqeek.model.SliderItem
import com.metehanbolat.thinqeek.viewmodel.MainActivityViewModel
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var firestore : FirebaseFirestore
    private lateinit var googleSignInClient : GoogleSignInClient
    private lateinit var viewModel : MainActivityViewModel

    companion object{
        private const val RC_SIGN_IN = 120
    }

    override fun onStart() {
        super.onStart()

        viewModel.checkCurrentUser(auth, baseContext, this@MainActivity)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Thinqeek)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        firestore = Firebase.firestore
        viewModel = MainActivityViewModel()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.googleButton.setOnClickListener {
            signIn()
        }

        viewModel.isLoading.observe(this){ isLoading ->
            if (isLoading){
                visibleLottie()
            }else{
                invisibleLottie()
            }
        }

        val sliderItems: MutableList<SliderItem> = ArrayList()
        viewModel.slideImageCreator(firestore, binding.viewPagerImageSlider, sliderItems)

    }

    private fun signIn() {
        viewModel.isLoading.value = true
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful){
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    viewModel.isLoading.value = false
                }
            }else{
                Toast.makeText(baseContext, exception.toString(), Toast.LENGTH_SHORT).show()
                viewModel.isLoading.value = false
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val intent = Intent(baseContext, ApplicationActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                viewModel.isLoading.value = false
            }
        }
    }

    private fun visibleLottie(){
        binding.googleButton.visibility = View.INVISIBLE
        binding.lottieWait.visibility = View.VISIBLE
    }

    private fun invisibleLottie(){
        binding.googleButton.visibility = View.VISIBLE
        binding.lottieWait.visibility = View.INVISIBLE
    }

}