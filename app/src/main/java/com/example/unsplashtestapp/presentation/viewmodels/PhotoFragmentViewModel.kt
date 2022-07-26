package com.example.unsplashtestapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.unsplashtestapp.domain.entitites.PhotoItem
import com.example.unsplashtestapp.domain.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PhotoFragmentViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    suspend fun fetchPhotos(id: String): Flow<PagingData<PhotoItem>> {
        return repository.letTopicPhotos(id = id)
            .cachedIn(viewModelScope)
    }
}