package com.example.unsplashtestapp.domain.entitites

import com.example.unsplashtestapp.domain.entitites.supportentities.CoverPhoto

data class TopicItem(
    val cover_photo: CoverPhoto,
    val description: String,
    val id: String,
    val last_collected_at: String,
    val `private`: Boolean,
    val published_at: String,
    val share_key: String,
    val title: String,
    val total_photos: Int,
    val updated_at: String,
)