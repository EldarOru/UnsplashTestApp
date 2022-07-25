package com.example.unsplashtestapp.domain.repositories

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.unsplashtestapp.data.repositories.MainRepositoryImpl
import com.example.unsplashtestapp.domain.entitites.PhotoItem
import com.example.unsplashtestapp.domain.entitites.TopicItem
import kotlinx.coroutines.flow.Flow


interface MainRepository {

    suspend fun letTopicsFlow(pagingConfig: PagingConfig = MainRepositoryImpl.getDefaultPageConfig()) : Flow<PagingData<TopicItem>>

    suspend fun letTopicPhotos(id: String,
                               pagingConfig: PagingConfig = MainRepositoryImpl.getDefaultPageConfig()) : Flow<PagingData<PhotoItem>>
}