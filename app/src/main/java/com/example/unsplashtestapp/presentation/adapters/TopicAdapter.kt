package com.example.unsplashtestapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplashtestapp.databinding.TopicItemBinding
import com.example.unsplashtestapp.domain.entitites.topic.TopicItem
import com.squareup.picasso.Picasso

class TopicAdapter(private val onTopicClickListener: ((TopicItem) -> Unit)? = null) : PagingDataAdapter<TopicItem, TopicAdapter.TopicPagedHolder>(DIFF_CALLBACK) {

    override fun onBindViewHolder(
        holder: TopicPagedHolder,
        position: Int
    ) {
        val topic: TopicItem? = getItem(position)
        holder.topicBinging.apply {
            topicName.text = topic?.title
            Picasso.get().load(topic?.cover_photo?.urls?.small).into(topicImage)

            root.setOnClickListener {
                if (topic != null) {
                    onTopicClickListener?.invoke(topic)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TopicPagedHolder {
        val topicView =
            TopicItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopicPagedHolder(topicView)
    }

    class TopicPagedHolder(val topicBinging: TopicItemBinding) :
        RecyclerView.ViewHolder(topicBinging.root)

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<TopicItem>() {
            override fun areItemsTheSame(
                oldConcert: TopicItem,
                newConcert: TopicItem
            ) = oldConcert.id == newConcert.id

            override fun areContentsTheSame(
                oldConcert: TopicItem,
                newConcert: TopicItem
            ) = oldConcert == newConcert
        }
    }
}