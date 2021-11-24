package com.metehanbolat.thinqeek.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.metehanbolat.thinqeek.databinding.NewsRecyclerRowBinding
import com.metehanbolat.thinqeek.model.News
import com.metehanbolat.thinqeek.viewmodel.NewsFragmentViewModel
import com.squareup.picasso.Picasso

class NewsRecyclerAdapter(var context : Context, var newsList : ArrayList<News>, var viewModel : NewsFragmentViewModel) : RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder>() {
    class NewsViewHolder(val binding : NewsRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.binding.newsTitle.text = newsList[position].title
        holder.binding.newsDescription.text = newsList[position].description
        Picasso.get().load(newsList[position].downloadUrl).into(holder.binding.newsImage)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
}