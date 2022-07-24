package com.example.unsplashtestapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplashtestapp.databinding.PhotoItemBinding
import com.example.unsplashtestapp.domain.entitites.photo.PhotoItem
import com.squareup.picasso.Picasso

class PhotoAdapter(private val onPhotoClickListener: ((PhotoItem) -> Unit)? = null)
    : PagingDataAdapter<PhotoItem, PhotoAdapter.PhotoPagedHolder>(DIFF_CALLBACK) {

    override fun onBindViewHolder(
        holder: PhotoPagedHolder,
        position: Int
    ) {
        val photo: PhotoItem? = getItem(position)
        holder.photoBinging.apply {
            Picasso.get().load(photo?.urls?.regular).into(photoImage)
            root.setOnClickListener {
                if (photo != null) {
                    onPhotoClickListener?.invoke(photo)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotoPagedHolder {
        val photoView =
            PhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoPagedHolder(photoView)
    }

    class PhotoPagedHolder(val photoBinging: PhotoItemBinding) :
        RecyclerView.ViewHolder(photoBinging.root)

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<PhotoItem>() {
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
}