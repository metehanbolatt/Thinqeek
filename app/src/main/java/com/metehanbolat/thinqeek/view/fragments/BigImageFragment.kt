package com.metehanbolat.thinqeek.view.fragments

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.metehanbolat.thinqeek.R
import com.metehanbolat.thinqeek.databinding.FragmentBigImageBinding
import com.squareup.picasso.Picasso

class BigImageFragment : Fragment() {

    private var _binding : FragmentBigImageBinding? = null
    private val binding get() = _binding!!

    private var downloadUrl = ""
    private var isWhat = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBigImageBinding.inflate(inflater, container, false)
        val view = binding.root

        arguments?.let {
            downloadUrl = BigImageFragmentArgs.fromBundle(it).downloadUrl!!
            isWhat = BigImageFragmentArgs.fromBundle(it).isWhat!!
        }

        Picasso.get().load(downloadUrl).into(binding.bigMovieImage)

        when(isWhat){
            "Movies" -> {
                binding.seriesOrMovieText.text = resources.getString(R.string.movie)
                binding.seriesOrMovieText.setTextColor(ContextCompat.getColor(requireContext(), R.color.movieColor))
            }
            "Series" ->  {
                binding.seriesOrMovieText.text = resources.getString(R.string.series)
                binding.seriesOrMovieText.setTextColor(ContextCompat.getColor(requireContext(), R.color.seriesColor))
            }
            else -> {
                binding.seriesOrMovieText.text = resources.getString(R.string.news)
                binding.seriesOrMovieText.setTextColor(ContextCompat.getColor(requireContext(), R.color.newsColor))
            }
        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val animation = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}