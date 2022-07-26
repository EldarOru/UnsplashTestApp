package com.example.unsplashtestapp.presentation.adapters

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<O : Any, B : ViewBinding>(
    protected open val clickListener: ((O) -> Unit)? = null,
    protected val diffCallback: DiffUtil.ItemCallback<O>
) : PagingDataAdapter<O, BaseAdapter<O, B>.ViewHolder>(diffCallback = diffCallback) {

    protected abstract fun initBinding(parent: ViewGroup, viewType: Int): B

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(initBinding(parent, viewType))
    }

    protected fun setClickListener(obj: O?, binding: B) {
        binding.root.setOnClickListener {
            if (obj != null) {
                clickListener?.invoke(obj)
            }
        }
    }

    inner class ViewHolder(val binding: B) : RecyclerView.ViewHolder(binding.root)

}