package com.islam.android.apps.movies.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * The base RecyclerView adapter class to configure some several configurations
 */
abstract class BaseAdapter<T, VB : ViewDataBinding, VH : RecyclerView.ViewHolder>(protected var data: List<T>) :
    RecyclerView.Adapter<VH>() {

    override fun getItemCount(): Int = data.size

    protected abstract val layout: Int

    /**
     * this method generates viewBinding reference for the item
     */
    protected open fun getItemViewBinding(parent: ViewGroup): VB {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
    }
}
