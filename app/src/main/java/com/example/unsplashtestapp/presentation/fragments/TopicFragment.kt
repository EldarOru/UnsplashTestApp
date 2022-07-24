package com.example.unsplashtestapp.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unsplashtestapp.databinding.TopicFragmentBinding
import com.example.unsplashtestapp.presentation.activities.OnFragmentInteractionsListener
import com.example.unsplashtestapp.presentation.adapters.LoaderStateAdapter
import com.example.unsplashtestapp.presentation.adapters.TopicAdapter
import com.example.unsplashtestapp.presentation.viewmodels.TopicFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.lang.RuntimeException

@AndroidEntryPoint
class TopicFragment: Fragment() {

    private lateinit var onFragmentsInteractionsListener: OnFragmentInteractionsListener
    private val topicFragmentViewModel: TopicFragmentViewModel by viewModels()
    private lateinit var topicFragmentBinding: TopicFragmentBinding
    private lateinit var topicAdapter: TopicAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionsListener){
            onFragmentsInteractionsListener = context
        }else{
            throw RuntimeException("Activity must implement OnFragmentInteractionsListener")
        }
    }

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
        setOnClick()
        setLoadStateListener()
    }

    private fun setRecyclerView() {
        val recyclerView = topicFragmentBinding.topicRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        topicAdapter = TopicAdapter(onTopicClickListener = {
            onFragmentsInteractionsListener.onAddBackStack(
                "new fragment",
                PhotoFragment.newInstancePhotoFragment(
                    id = it.id
                )
            )
        })
        recyclerView.adapter = topicAdapter.withLoadStateFooter(
            footer = LoaderStateAdapter{topicAdapter.retry()}
        )
    }

    private fun fetchTopics() {
        viewLifecycleOwner.lifecycleScope.launch {
            topicFragmentViewModel.fetchTopics().distinctUntilChanged().collectLatest {
                topicAdapter.submitData(it)
            }
        }
    }

    private fun setOnClick() {
        topicFragmentBinding.btnRetry.setOnClickListener {
            topicAdapter.retry()
        }
    }

    private fun setLoadStateListener(){
        topicAdapter.addLoadStateListener {
            if (it.refresh is LoadState.Loading){
                topicFragmentBinding.btnRetry.visibility = View.GONE

                topicFragmentBinding.progressBar.visibility = View.VISIBLE
            }
            else {
                topicFragmentBinding.progressBar.visibility = View.GONE
                val errorState = when {
                    it.append is LoadState.Error -> it.append as LoadState.Error
                    it.prepend is LoadState.Error -> it.prepend as LoadState.Error
                    it.refresh is LoadState.Error -> {
                        topicFragmentBinding.btnRetry.visibility = View.VISIBLE
                        it.refresh as LoadState.Error
                    }
                    else -> null
                }
                errorState?.let { err ->
                    Toast.makeText(requireContext(), err.error.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}