package com.example.unsplashtestapp.domain.repositories

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.unsplashtestapp.data.repositories.TopicRepositoryImpl
import com.example.unsplashtestapp.domain.entitites.photo.PhotoItem
import com.example.unsplashtestapp.domain.entitites.topic.TopicItem
import kotlinx.coroutines.flow.Flow


interface TopicRepository {

    suspend fun letTopicsFlow(pagingConfig: PagingConfig = TopicRepositoryImpl.getDefaultPageConfig()) : Flow<PagingData<TopicItem>>

    suspend fun letTopicPhotos(id: Int,
                               pagingConfig: PagingConfig = TopicRepositoryImpl.getDefaultPageConfig()) : Flow<PagingData<PhotoItem>>
}