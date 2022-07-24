package com.example.unsplashtestapp.data.datasources

import androidx.paging.PagingSource
import com.example.unsplashtestapp.data.network.RetrofitClient
import com.example.unsplashtestapp.data.repositories.TopicRepositoryImpl.Companion.DEFAULT_PAGE_INDEX
import com.example.unsplashtestapp.domain.entitites.topic.TopicItem
import retrofit2.HttpException
import java.io.IOException

class TopicDataSource constructor(private val retrofitClient: RetrofitClient) :
    PagingSource<Int, TopicItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TopicItem> {
        val page = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            val response = retrofitClient.retrofitServices.getTopics(page = page)
            LoadResult.Page(
                response, prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}