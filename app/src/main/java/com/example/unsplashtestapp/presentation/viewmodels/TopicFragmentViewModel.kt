package com.example.unsplashtestapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.unsplashtestapp.domain.entitites.TopicItem
import com.example.unsplashtestapp.domain.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TopicFragmentViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    suspend fun fetchTopics(): Flow<PagingData<TopicItem>> {
        return repository.letTopicsFlow()
            .cachedIn(viewModelScope)
    }
}