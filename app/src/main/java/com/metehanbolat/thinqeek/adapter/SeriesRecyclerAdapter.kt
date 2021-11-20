package com.metehanbolat.thinqeek.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.metehanbolat.thinqeek.databinding.SeriesRecyclerRowBinding
import com.metehanbolat.thinqeek.model.Series
import com.metehanbolat.thinqeek.view.fragments.MoviesFragmentDirections
import com.metehanbolat.thinqeek.viewmodel.SeriesFragmentViewModel
import com.squareup.picasso.Picasso

class SeriesRecyclerAdapter(var context : Context, var seriesList : ArrayList<Series>, var viewModel : SeriesFragmentViewModel) : RecyclerView.Adapter<SeriesRecyclerAdapter.SeriesViewHolder>() {
    class SeriesViewHolder(val binding : SeriesRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        val binding = SeriesRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SeriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        holder.binding.seriesName.text = seriesList[position].name
        holder.binding.seriesYear.text = seriesList[position].comment
        holder.binding.seriesRate.text = seriesList[position].rate.toString()
        holder.binding.seriesSeason.text = seriesList[position].season.toString()
        Picasso.get().load(seriesList[position].downloadUrl).into(holder.binding.seriesImage)

        holder.binding.seriesRecyclerConstraint.setOnClickListener {
            if (viewModel.isClickable.value == true){
                val action = MoviesFragmentDirections.actionMoviesFragmentToDetailsMovieFragment(
                    director = seriesList[position].director,
                    name = seriesList[position].name,
                    comment = seriesList[position].comment,
                    downloadUrl = seriesList[position].downloadUrl,
                    rate = seriesList[position].rate.toString(),
                    season = seriesList[position].season.toString(),
                    year = seriesList[position].year.toString()
                )
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}