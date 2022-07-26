package com.example.unsplashtestapp.data.datasources

import androidx.paging.PagingSource
import com.example.unsplashtestapp.data.network.RetrofitClient
import com.example.unsplashtestapp.data.repositories.MainRepositoryImpl
import com.example.unsplashtestapp.domain.entitites.PhotoItem
import retrofit2.HttpException
import java.io.IOException

class PhotoDataSource constructor(
    private val retrofitClient: RetrofitClient,
    private val id: String
) : PagingSource<Int, PhotoItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoItem> {
        val page = params.key ?: MainRepositoryImpl.DEFAULT_PAGE_INDEX
        return try {
            val response = retrofitClient.retrofitServices.getTopicPhotos(id = id, page = page)
            LoadResult.Page(
                response,
                prevKey = if (page == MainRepositoryImpl.DEFAULT_PAGE_INDEX) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}