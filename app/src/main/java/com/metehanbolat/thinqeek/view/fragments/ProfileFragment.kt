package com.metehanbolat.thinqeek.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.metehanbolat.thinqeek.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var userName : String
    private lateinit var userEmail : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        arguments.let {
            if (it != null){
                userName = ProfileFragmentArgs.fromBundle(it).userName
                userEmail = ProfileFragmentArgs.fromBundle(it).userEmail
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.userName.text = userName
        binding.userEmail.text = userEmail
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}