package com.metehanbolat.thinqeek.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.metehanbolat.thinqeek.adapter.MoviesRecyclerAdapter
import com.metehanbolat.thinqeek.databinding.FragmentMoviesBinding
import com.metehanbolat.thinqeek.model.Movies
import com.metehanbolat.thinqeek.view.activities.MainActivity
import com.metehanbolat.thinqeek.viewmodel.MoviesFragmentViewModel

class MoviesFragment : Fragment() {

    private var _binding : FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MoviesFragmentViewModel
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var movieList: ArrayList<Movies>
    private lateinit var movieAdapter: MoviesRecyclerAdapter

    private lateinit var userName : String
    private lateinit var userEmail : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = MoviesFragmentViewModel()
        firestore = Firebase.firestore
        auth = Firebase.auth
        movieList = ArrayList()
        movieAdapter = MoviesRecyclerAdapter(requireContext(), movieList, viewModel)

        if (auth.currentUser != null){
            userName = auth.currentUser!!.displayName.toString()
            userEmail = auth.currentUser!!.email.toString()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFilm("date", firestore, movieList, movieAdapter)
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2,
            StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerView.adapter = movieAdapter

        binding.exitImage.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
            auth.signOut()
        }

        binding.chipYear.setOnCheckedChangeListener { _, b ->
            if (b){
                binding.chipRate.isChecked = false
                viewModel.isClickable.value = false
                viewModel.getFilm("year",firestore, movieList, movieAdapter)
            }else{
                viewModel.isClickable.value = false
                viewModel.getFilm("date", firestore, movieList, movieAdapter)
            }
        }

        binding.chipRate.setOnCheckedChangeListener { _, b ->
            if (b){
                binding.chipYear.isChecked = false
                viewModel.isClickable.value = false
                viewModel.getFilm("rate",firestore, movieList, movieAdapter)
            }else{
                viewModel.isClickable.value = false
                viewModel.getFilm("date", firestore, movieList, movieAdapter)
            }
        }

        binding.profileImage.setOnClickListener {
            val action = MoviesFragmentDirections.actionMoviesFragmentToProfileFragment(userName, userEmail)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}