package com.example.unsplashtestapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.unsplashtestapp.databinding.TopicItemBinding
import com.example.unsplashtestapp.domain.entitites.TopicItem
import com.squareup.picasso.Picasso

class TopicAdapter(
    override val clickListener: ((TopicItem) -> Unit)?
) : BaseAdapter<TopicItem, TopicItemBinding>(
    clickListener = clickListener,
    diffCallback = TopicCallback()
) {

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val topic: TopicItem? = getItem(position)
        holder.binding.apply {
            topicName.text = topic?.title
            Picasso.get().load(topic?.cover_photo?.urls?.small).into(topicImage)
        }
        setClickListener(topic, holder.binding)
    }

    override fun initBinding(parent: ViewGroup, viewType: Int): TopicItemBinding =
        TopicItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    class TopicCallback : DiffUtil.ItemCallback<TopicItem>() {
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