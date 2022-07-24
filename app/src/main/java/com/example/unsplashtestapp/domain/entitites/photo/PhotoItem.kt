package com.example.unsplashtestapp.domain.entitites.photo

import com.example.unsplashtestapp.domain.entitites.topic.Urls

data class PhotoItem(
    val id: String,
    val likes: Int,
    val urls: Urls
)
