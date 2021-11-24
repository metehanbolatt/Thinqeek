package com.metehanbolat.thinqeek.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.metehanbolat.thinqeek.R
import com.metehanbolat.thinqeek.databinding.FragmentContentDetailsBinding
import com.metehanbolat.thinqeek.viewmodel.ContentDetailsFragmentViewModel
import com.squareup.picasso.Picasso

class ContentDetailsFragment : Fragment() {

    private var _binding : FragmentContentDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var firestore: FirebaseFirestore
    private lateinit var viewModel: ContentDetailsFragmentViewModel

    private var movieName = ""
    private var movieType = ""
    private var movieDirector = ""
    private var movieStars = ""
    private var movieTime = ""
    private var movieYear = ""
    private var movieSeason = ""
    private var movieSubject = ""
    private var movieComment = ""
    private var movieDownloadUrl = ""
    private var movieRate = ""
    private var isMovie = true
    private var author = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContentDetailsBinding.inflate(inflater, container, false)
        val view = binding.root

        firestore = Firebase.firestore
        viewModel = ContentDetailsFragmentViewModel()

        arguments?.let { bundle ->
            movieName = ContentDetailsFragmentArgs.fromBundle(bundle).name!!
            movieComment = ContentDetailsFragmentArgs.fromBundle(bundle).comment!!
            movieDownloadUrl = ContentDetailsFragmentArgs.fromBundle(bundle).downloadUrl!!
            movieRate = ContentDetailsFragmentArgs.fromBundle(bundle).rate!!
            movieType = ContentDetailsFragmentArgs.fromBundle(bundle).type!!
            movieDirector = ContentDetailsFragmentArgs.fromBundle(bundle).director!!
            movieStars = ContentDetailsFragmentArgs.fromBundle(bundle).stars!!
            movieTime = ContentDetailsFragmentArgs.fromBundle(bundle).time!!
            movieYear = ContentDetailsFragmentArgs.fromBundle(bundle).year!!
            movieSubject = ContentDetailsFragmentArgs.fromBundle(bundle).subject!!
            isMovie = ContentDetailsFragmentArgs.fromBundle(bundle).isMovie
            author = ContentDetailsFragmentArgs.fromBundle(bundle).author!!
            movieSeason = ContentDetailsFragmentArgs.fromBundle(bundle).season!!
        }

        if (isMovie){
            binding.seasonLinear.visibility = View.GONE
            binding.movieOrSeriesText.text = resources.getString(R.string.movie)
            binding.movieOrSeriesText.setTextColor(ContextCompat.getColor(requireContext(), R.color.movieColor))
        }else{
            binding.seasonLinear.visibility = View.VISIBLE
            binding.movieSeason.text = movieSeason
            binding.movieOrSeriesText.text = resources.getString(R.string.series)
            binding.movieOrSeriesText.setTextColor(ContextCompat.getColor(requireContext(), R.color.seriesColor))
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.movieNameText.text = movieName
        binding.movieDirector.text = movieDirector
        binding.movieStars.text = movieStars
        binding.movieType.text = movieType
        binding.movieTime.text = movieTime
        binding.movieYear.text = movieYear
        binding.movieSubject.text = movieSubject
        binding.detailsContentComment.text = movieComment
        Picasso.get().load(movieDownloadUrl).into(binding.contentImage)
        binding.author.text = author

        starCount(movieRate.toDouble())

        binding.contentImage.setOnClickListener {
            val extras = FragmentNavigatorExtras(binding.contentImage to "image_big")
            val action = ContentDetailsFragmentDirections.actionDetailsMovieFragmentToBigMovieImageFragment(movieDownloadUrl, isMovie)
            findNavController().navigate(action,extras)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun starCount(rate : Double){

        if (rate <= 2 && rate > 0){
            if (rate == 2.0){
                binding.firstStar.setImageResource(R.drawable.ic_orange_star)
            }else{
                binding.firstStar.setImageResource(R.drawable.ic_half_star)
            }
        }else if (rate <= 4 && rate > 2){
            if (rate == 4.0){
                binding.firstStar.setImageResource(R.drawable.ic_orange_star)
                binding.secondStar.setImageResource(R.drawable.ic_orange_star)
            }else{
                binding.firstStar.setImageResource(R.drawable.ic_orange_star)
                binding.secondStar.setImageResource(R.drawable.ic_half_star)
            }
        }else if (rate <= 6 && rate > 4){
            if (rate == 6.0){
                binding.firstStar.setImageResource(R.drawable.ic_orange_star)
                binding.secondStar.setImageResource(R.drawable.ic_orange_star)
                binding.thirdStar.setImageResource(R.drawable.ic_orange_star)
            }else{
                binding.firstStar.setImageResource(R.drawable.ic_orange_star)
                binding.secondStar.setImageResource(R.drawable.ic_orange_star)
                binding.thirdStar.setImageResource(R.drawable.ic_half_star)
            }
        }else if (rate <= 8 && rate > 6){
            if (rate == 8.0){
                binding.firstStar.setImageResource(R.drawable.ic_orange_star)
                binding.secondStar.setImageResource(R.drawable.ic_orange_star)
                binding.thirdStar.setImageResource(R.drawable.ic_orange_star)
                binding.fourthStar.setImageResource(R.drawable.ic_orange_star)
            }else{
                binding.firstStar.setImageResource(R.drawable.ic_orange_star)
                binding.secondStar.setImageResource(R.drawable.ic_orange_star)
                binding.thirdStar.setImageResource(R.drawable.ic_orange_star)
                binding.fourthStar.setImageResource(R.drawable.ic_half_star)
            }
        }else if (rate <= 10 && rate > 8){
            if (rate == 10.0){
                binding.firstStar.setImageResource(R.drawable.ic_orange_star)
                binding.secondStar.setImageResource(R.drawable.ic_orange_star)
                binding.thirdStar.setImageResource(R.drawable.ic_orange_star)
                binding.fourthStar.setImageResource(R.drawable.ic_orange_star)
                binding.fifthStar.setImageResource(R.drawable.ic_orange_star)
            }else{
                binding.firstStar.setImageResource(R.drawable.ic_orange_star)
                binding.secondStar.setImageResource(R.drawable.ic_orange_star)
                binding.thirdStar.setImageResource(R.drawable.ic_orange_star)
                binding.fourthStar.setImageResource(R.drawable.ic_orange_star)
                binding.fifthStar.setImageResource(R.drawable.ic_half_star)
            }
        }
    }

}