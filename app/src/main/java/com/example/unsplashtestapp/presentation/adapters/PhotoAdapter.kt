package com.example.unsplashtestapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplashtestapp.databinding.PhotoItemBinding
import com.example.unsplashtestapp.databinding.TopicItemBinding
import com.example.unsplashtestapp.domain.entitites.PhotoItem
import com.example.unsplashtestapp.domain.entitites.TopicItem
import com.squareup.picasso.Picasso

class PhotoAdapter(override val clickListener: ((PhotoItem) -> Unit)?
): BaseAdapter<PhotoItem, PhotoItemBinding>(
    clickListener = clickListener,
    diffCallback = PhotoCallback()){

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val photo: PhotoItem? = getItem(position)
        holder.binding.apply {
            Picasso.get().load(photo?.urls?.regular).into(photoImage)
        }
        setClickListener(photo, holder.binding)
    }

    override fun initBinding(parent: ViewGroup, viewType: Int): PhotoItemBinding
            = PhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    class PhotoCallback: DiffUtil.ItemCallback<PhotoItem>() {
        override fun areItemsTheSame(
            oldConcert: PhotoItem,
            newConcert: PhotoItem
        ) = oldConcert.id == newConcert.id

        override fun areContentsTheSame(
            oldConcert: PhotoItem,
            newConcert: PhotoItem
        ) = oldConcert == newConcert
    }
}