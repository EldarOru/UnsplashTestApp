package com.example.unsplashtestapp.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unsplashtestapp.databinding.PhotoFragmentBinding
import com.example.unsplashtestapp.databinding.TopicFragmentBinding
import com.example.unsplashtestapp.presentation.activities.OnFragmentInteractionsListener
import com.example.unsplashtestapp.presentation.adapters.LoaderStateAdapter
import com.example.unsplashtestapp.presentation.adapters.PhotoAdapter
import com.example.unsplashtestapp.presentation.adapters.TopicAdapter
import com.example.unsplashtestapp.presentation.viewmodels.PhotoFragmentViewModel
import com.example.unsplashtestapp.presentation.viewmodels.TopicFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.lang.RuntimeException

@AndroidEntryPoint
class PhotoFragment: Fragment() {

    private lateinit var onFragmentsInteractionsListener: OnFragmentInteractionsListener
    private val photoFragmentViewModel: PhotoFragmentViewModel by viewModels()
    private lateinit var photoFragmentBinding: PhotoFragmentBinding
    private lateinit var photoAdapter: PhotoAdapter
    private var comeId = ""

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
        photoFragmentBinding = PhotoFragmentBinding.inflate(inflater, container, false)
        return photoFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        fetchTopics()
        setLoadStateListener()
    }

    private fun setRecyclerView() {
        val recyclerView = photoFragmentBinding.photoRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        photoAdapter = PhotoAdapter(onPhotoClickListener = {
            onFragmentsInteractionsListener.onAddBackStack(
                "new fragment",
                DetailedPhotoFragment.newInstanceDetailedPhotoFragment(
                    url = it.urls.full
                )
            )
        })
        recyclerView.adapter = photoAdapter.withLoadStateFooter(
            footer = LoaderStateAdapter{photoAdapter.retry()}
        )
    }

    private fun fetchTopics() {
        comeId = requireArguments().getString(ID_TAG).toString()
        lifecycleScope.launch {
            photoFragmentViewModel.fetchPhotos(comeId).distinctUntilChanged().collectLatest {
                photoAdapter.submitData(it)
            }
        }
    }

    private fun setLoadStateListener(){
        photoAdapter.addLoadStateListener {
            if (it.refresh is LoadState.Loading){
                photoFragmentBinding.btnRetry.visibility = View.GONE

                photoFragmentBinding.progressBar.visibility = View.VISIBLE
            }
            else {
                photoFragmentBinding.progressBar.visibility = View.GONE
                val errorState = when {
                    it.append is LoadState.Error -> it.append as LoadState.Error
                    it.prepend is LoadState.Error -> it.prepend as LoadState.Error
                    it.refresh is LoadState.Error -> {
                        photoFragmentBinding.btnRetry.visibility = View.VISIBLE
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

    companion object {
        fun newInstancePhotoFragment(id: String): PhotoFragment{
            return PhotoFragment().apply {
                arguments = Bundle().apply {
                    putString(ID_TAG, id)
                }
            }
        }
        private const val ID_TAG = "ID"
    }
}