package com.islam.android.apps.movies.ui.home

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.islam.android.apps.movies.R
import com.islam.android.apps.movies.base.BaseAdapter
import com.islam.android.apps.movies.databinding.ItemGenreBinding
import com.islam.android.apps.movies.pojo.Genre

class GenresAdapter(data: List<Genre>, private val callback: GenresCallback) :
    BaseAdapter<Genre, ItemGenreBinding, GenresAdapter.ItemGenreViewHolder>(
        data
    ) {
    override val layout: Int
        get() = R.layout.item_genre

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemGenreViewHolder {
        return ItemGenreViewHolder(getItemViewBinding(parent))
    }

    override fun onBindViewHolder(holder: ItemGenreViewHolder, position: Int) {
        holder.viewBinding.genre = data[position]
    }

    inner class ItemGenreViewHolder(val viewBinding: ItemGenreBinding) :
        ViewHolder(viewBinding.root) {
        init {
            viewBinding.root.setOnClickListener {
                callback.onGenreSelected(data[layoutPosition], layoutPosition)
            }
        }
    }

    interface GenresCallback {
        fun onGenreSelected(genre: Genre, position: Int)
    }
}