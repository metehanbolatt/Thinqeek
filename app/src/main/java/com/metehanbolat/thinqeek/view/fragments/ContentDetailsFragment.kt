package com.metehanbolat.thinqeek.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
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

    private var contentName = ""
    private var contentType = ""
    private var contentDirector = ""
    private var contentStars = ""
    private var contentTime = ""
    private var contentYear = ""
    private var contentSeason = ""
    private var contentSubject = ""
    private var contentComment = ""
    private var contentDownloadUrl = ""
    private var contentRate = ""
    private var isWhat = ""
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
            contentName = ContentDetailsFragmentArgs.fromBundle(bundle).name!!
            contentComment = ContentDetailsFragmentArgs.fromBundle(bundle).comment!!
            contentDownloadUrl = ContentDetailsFragmentArgs.fromBundle(bundle).downloadUrl!!
            contentRate = ContentDetailsFragmentArgs.fromBundle(bundle).rate!!
            contentType = ContentDetailsFragmentArgs.fromBundle(bundle).type!!
            contentDirector = ContentDetailsFragmentArgs.fromBundle(bundle).director!!
            contentStars = ContentDetailsFragmentArgs.fromBundle(bundle).stars!!
            contentTime = ContentDetailsFragmentArgs.fromBundle(bundle).time!!
            contentYear = ContentDetailsFragmentArgs.fromBundle(bundle).year!!
            contentSubject = ContentDetailsFragmentArgs.fromBundle(bundle).subject!!
            isWhat = ContentDetailsFragmentArgs.fromBundle(bundle).isWhat!!
            author = ContentDetailsFragmentArgs.fromBundle(bundle).author!!
            contentSeason = ContentDetailsFragmentArgs.fromBundle(bundle).season!!
        }

        when(isWhat){
            "Movies" -> {
                binding.seasonLinear.visibility = View.GONE
                binding.movieOrSeriesText.text = resources.getString(R.string.movie)
                binding.movieOrSeriesText.setTextColor(ContextCompat.getColor(requireContext(), R.color.movieColor))
            }
            "Series" ->  {
                binding.seasonLinear.visibility = View.VISIBLE
                binding.movieSeason.text = contentSeason
                binding.movieOrSeriesText.text = resources.getString(R.string.series)
                binding.movieOrSeriesText.setTextColor(ContextCompat.getColor(requireContext(), R.color.seriesColor))
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.movieNameText.text = contentName
        binding.movieDirector.text = contentDirector
        binding.movieStars.text = contentStars
        binding.movieType.text = contentType
        binding.movieTime.text = contentTime
        binding.movieYear.text = contentYear
        binding.movieSubject.text = contentSubject
        binding.detailsContentComment.text = contentComment
        Picasso.get().load(contentDownloadUrl).into(binding.contentImage)
        binding.author.text = author

        starCount(contentRate.toDouble())

        binding.contentImage.setOnClickListener {
            val extras = FragmentNavigatorExtras(binding.contentImage to "image_big")
            val action = ContentDetailsFragmentDirections.actionDetailsMovieFragmentToBigMovieImageFragment(contentDownloadUrl, isWhat)
            findNavController().navigate(action,extras)
        }

        binding.userCommentButton.setOnClickListener {
            val action = ContentDetailsFragmentDirections.actionContentDetailsFragmentToChatUserFragment(isWhat, contentName)
            findNavController().navigate(action)
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