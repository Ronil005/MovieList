package com.app.movielist.views.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.movielist.R
import com.app.movielist.databinding.MovieRowBinding
import com.app.movielist.models.PopularMoviesResponse
import com.app.movielist.models.Results

class MovieAdapter(
    val popularMoviesList: PopularMoviesResponse,
    val onClickListener: OnClickListener
) :
    RecyclerView.Adapter<MovieAdapter.MyAdapter>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter {
        val movieRow = LayoutInflater.from(parent.context)
        val movieRowBinding =
            DataBindingUtil.inflate<MovieRowBinding>(movieRow, R.layout.movie_row, parent, false)
        return MyAdapter(movieRowBinding)
    }

    override fun onBindViewHolder(holder: MyAdapter, position: Int) {
        val popularMoviesResponse = popularMoviesList.results[position]
        holder.movieRowBinding.resultModel = popularMoviesResponse
        holder.movieRowBinding.executePendingBindings()

        holder.movieRowBinding.ivMovie.setOnClickListener {
            onClickListener.onMovieClick(it, popularMoviesResponse)
        }
    }

    override fun getItemCount() = popularMoviesList.results.size

    class MyAdapter(val movieRowBinding: MovieRowBinding) :
        RecyclerView.ViewHolder(movieRowBinding.root) {
    }

    interface OnClickListener {
        fun onMovieClick(it: View, popularMoviesResponse: Results)
    }
}
