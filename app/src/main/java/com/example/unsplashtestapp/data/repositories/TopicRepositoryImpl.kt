package com.example.unsplashtestapp.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.unsplashtestapp.data.datasources.TopicDataSource
import com.example.unsplashtestapp.data.network.RetrofitClient
import com.example.unsplashtestapp.domain.entitites.photo.PhotoItem
import com.example.unsplashtestapp.domain.entitites.topic.TopicItem
import com.example.unsplashtestapp.domain.repositories.TopicRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TopicRepositoryImpl @Inject constructor(
    private val retrofitClient: RetrofitClient
) : TopicRepository {

    override suspend fun letTopicsFlow(pagingConfig: PagingConfig): Flow<PagingData<TopicItem>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { TopicDataSource(retrofitClient) }
        ).flow
    }

    override suspend fun letTopicPhotos(
        id: Int,
        pagingConfig: PagingConfig
    ): Flow<PagingData<PhotoItem>> {
        /*
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { TopicDataSource(retrofitClient) }
        ).flow
         */
        TODO()
    }

    companion object {
        const val DEFAULT_PAGE_INDEX = 1
        private const val DEFAULT_PAGE_SIZE = 10

        fun getDefaultPageConfig(): PagingConfig {
            return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true)
        }
    }
}