package com.example.unsplashtestapp.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unsplashtestapp.databinding.PhotoFragmentBinding
import com.example.unsplashtestapp.presentation.activities.OnFragmentInteractionsListener
import com.example.unsplashtestapp.presentation.adapters.LoaderStateAdapter
import com.example.unsplashtestapp.presentation.adapters.PhotoAdapter
import com.example.unsplashtestapp.presentation.viewmodels.PhotoFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PhotoFragment : BaseFragment<PhotoFragmentBinding>() {

    private lateinit var onFragmentsInteractionsListener: OnFragmentInteractionsListener
    private val photoFragmentViewModel: PhotoFragmentViewModel by viewModels()
    private lateinit var photoAdapter: PhotoAdapter
    private var comeId = ""

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionsListener) {
            onFragmentsInteractionsListener = context
        } else {
            throw RuntimeException("Activity must implement OnFragmentInteractionsListener")
        }
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): PhotoFragmentBinding = PhotoFragmentBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        fetchTopics()
        setLoadStateListener()
        setOnClick()
    }

    private fun setRecyclerView() {
        val recyclerView = binding.photoRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        photoAdapter = PhotoAdapter(clickListener = {
            onFragmentsInteractionsListener.onAddBackStack(
                "new fragment",
                DetailedPhotoFragment.newInstanceDetailedPhotoFragment(
                    url = it.urls.regular
                )
            )
        })
        recyclerView.adapter = photoAdapter.withLoadStateFooter(
            footer = LoaderStateAdapter { photoAdapter.retry() }
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

    private fun setOnClick() {
        binding.btnRetry.setOnClickListener {
            photoAdapter.retry()
        }
    }

    private fun setLoadStateListener() {
        photoAdapter.addLoadStateListener {
            if (it.refresh is LoadState.Loading) {
                binding.btnRetry.visibility = View.GONE

                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
                val errorState = when {
                    it.append is LoadState.Error -> it.append as LoadState.Error
                    it.prepend is LoadState.Error -> it.prepend as LoadState.Error
                    it.refresh is LoadState.Error -> {
                        binding.btnRetry.visibility = View.VISIBLE
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
        fun newInstancePhotoFragment(id: String): PhotoFragment {
            return PhotoFragment().apply {
                arguments = Bundle().apply {
                    putString(ID_TAG, id)
                }
            }
        }

        private const val ID_TAG = "ID"
    }
}