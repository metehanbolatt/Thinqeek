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
import com.metehanbolat.thinqeek.R
import com.metehanbolat.thinqeek.adapter.SeriesRecyclerAdapter
import com.metehanbolat.thinqeek.databinding.FragmentSeriesBinding
import com.metehanbolat.thinqeek.model.Series
import com.metehanbolat.thinqeek.view.activities.MainActivity
import com.metehanbolat.thinqeek.viewmodel.SeriesFragmentViewModel

class SeriesFragment : Fragment() {

    private var _binding : FragmentSeriesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SeriesFragmentViewModel
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var seriesList: ArrayList<Series>
    private lateinit var seriesAdapter: SeriesRecyclerAdapter

    private lateinit var userName : String
    private lateinit var userEmail : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSeriesBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = SeriesFragmentViewModel()
        firestore = Firebase.firestore
        auth = Firebase.auth
        seriesList = ArrayList()
        seriesAdapter = SeriesRecyclerAdapter(requireContext(), seriesList, viewModel)

        if (auth.currentUser != null){
            userName = auth.currentUser!!.displayName.toString()
            userEmail = auth.currentUser!!.email.toString()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSeries("date", firestore, seriesList, seriesAdapter)
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2,
            StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerView.adapter = seriesAdapter

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
                viewModel.getSeries("year",firestore, seriesList, seriesAdapter)
            }else{
                viewModel.isClickable.value = false
                viewModel.getSeries("date", firestore, seriesList, seriesAdapter)
            }
        }

        binding.chipRate.setOnCheckedChangeListener { _, b ->
            if (b){
                binding.chipYear.isChecked = false
                viewModel.isClickable.value = false
                viewModel.getSeries("rate",firestore, seriesList, seriesAdapter)
            }else{
                viewModel.isClickable.value = false
                viewModel.getSeries("date", firestore, seriesList, seriesAdapter)
            }
        }

        binding.profileImage.setOnClickListener {
            val action = SeriesFragmentDirections.actionSeriesFragmentToProfileFragment(userName, userEmail)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}