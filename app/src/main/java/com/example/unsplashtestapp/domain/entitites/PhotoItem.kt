package com.example.unsplashtestapp.domain.entitites

import com.example.unsplashtestapp.domain.entitites.supportentities.Urls

data class PhotoItem(
    val id: String,
    val likes: Int,
    val urls: Urls
)
