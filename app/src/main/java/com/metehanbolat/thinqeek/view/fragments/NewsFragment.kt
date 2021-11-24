package com.metehanbolat.thinqeek.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.metehanbolat.thinqeek.adapter.MoviesRecyclerAdapter
import com.metehanbolat.thinqeek.adapter.NewsRecyclerAdapter
import com.metehanbolat.thinqeek.databinding.FragmentNewsBinding
import com.metehanbolat.thinqeek.model.Movies
import com.metehanbolat.thinqeek.model.News
import com.metehanbolat.thinqeek.view.activities.MainActivity
import com.metehanbolat.thinqeek.viewmodel.MoviesFragmentViewModel
import com.metehanbolat.thinqeek.viewmodel.NewsFragmentViewModel

class NewsFragment : Fragment() {

    private var _binding : FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: NewsFragmentViewModel
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var newsList: ArrayList<News>
    private lateinit var newsAdapter: NewsRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = NewsFragmentViewModel()
        firestore = Firebase.firestore
        auth = Firebase.auth
        newsList = ArrayList()
        newsAdapter = NewsRecyclerAdapter(requireContext(), newsList, viewModel)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getNews("date", firestore, newsList, newsAdapter)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = newsAdapter

        binding.exitImage.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
            auth.signOut()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}