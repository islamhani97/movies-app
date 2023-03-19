package com.islam.android.apps.movies.ui.home

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.islam.android.apps.movies.R
import com.islam.android.apps.movies.base.BaseAdapter
import com.islam.android.apps.movies.databinding.ItemMovieBinding
import com.islam.android.apps.movies.pojo.Movie

class MoviesAdapter(data: List<Movie>, private val callback: MoviesCallback) :
    BaseAdapter<Movie, ItemMovieBinding, MoviesAdapter.ItemMovieViewHolder>(data) {
    override val layout: Int
        get() = R.layout.item_movie

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMovieViewHolder {
        return ItemMovieViewHolder(getItemViewBinding(parent))
    }

    override fun onBindViewHolder(holder: ItemMovieViewHolder, position: Int) {
        holder.viewBinding.movie = data[position]
    }

    fun appendToDataSet(data: List<Movie>) {
        val positionStart = itemCount
        (this.data as ArrayList).addAll(data)
        notifyItemRangeInserted(positionStart, itemCount)
    }

    inner class ItemMovieViewHolder(val viewBinding: ItemMovieBinding) :
        ViewHolder(viewBinding.root) {
        init {
            viewBinding.root.setOnClickListener {
                callback.onMovieClicked(data[layoutPosition])
            }
        }
    }

    interface MoviesCallback {
        fun onMovieClicked(movie: Movie)
    }
}