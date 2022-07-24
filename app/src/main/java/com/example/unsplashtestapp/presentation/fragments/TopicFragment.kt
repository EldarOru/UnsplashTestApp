package com.example.unsplashtestapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unsplashtestapp.databinding.TopicFragmentBinding
import com.example.unsplashtestapp.presentation.adapters.LoaderStateAdapter
import com.example.unsplashtestapp.presentation.adapters.TopicAdapter
import com.example.unsplashtestapp.presentation.viewmodels.TopicFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TopicFragment: Fragment() {

    private val topicFragmentViewModel: TopicFragmentViewModel by viewModels()
    private lateinit var topicFragmentBinding: TopicFragmentBinding
    private lateinit var topicAdapter: TopicAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        topicFragmentBinding = TopicFragmentBinding.inflate(inflater, container, false)
        return topicFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        fetchTopics()

    }

    private fun setRecyclerView() {
        val recyclerView = topicFragmentBinding.topicRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        topicAdapter = TopicAdapter()
        recyclerView.adapter = topicAdapter.withLoadStateFooter(
            footer = LoaderStateAdapter{topicAdapter.retry()}
        )
    }

    private fun fetchTopics() {
        lifecycleScope.launch {
            topicFragmentViewModel.fetchTopics().distinctUntilChanged().collectLatest {
                topicAdapter.submitData(it)
            }
        }
    }
}