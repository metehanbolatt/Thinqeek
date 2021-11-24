package com.metehanbolat.thinqeek.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.metehanbolat.thinqeek.R
import com.metehanbolat.thinqeek.databinding.FragmentNewsDetailsBinding
import com.squareup.picasso.Picasso

class NewsDetailsFragment : Fragment() {

    private var _binding : FragmentNewsDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    private var newsTitle = ""
    private var newsDescription = ""
    private var newsImageUrl = ""
    private var author = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsDetailsBinding.inflate(inflater, container, false)
        val view = binding.root

        auth = Firebase.auth

        arguments?.let {
            newsTitle = NewsDetailsFragmentArgs.fromBundle(it).title!!
            newsDescription = NewsDetailsFragmentArgs.fromBundle(it).description!!
            newsImageUrl = NewsDetailsFragmentArgs.fromBundle(it).downloadUrl!!
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.newsDetailsTitle.text = newsTitle
        binding.newsDetailsDescription.text = newsDescription
        Picasso.get().load(newsImageUrl).into(binding.newsDetailsImage)
        binding.author.text = auth.currentUser!!.displayName

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}