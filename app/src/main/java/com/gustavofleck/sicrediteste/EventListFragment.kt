package com.gustavofleck.sicrediteste

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.gustavofleck.domain.models.SimplifiedEvent
import com.gustavofleck.presentation.viewmodels.EventListViewModel
import com.gustavofleck.presentation.viewstates.EventListViewState
import com.gustavofleck.sicrediteste.adapters.EventListAdapter
import com.gustavofleck.sicrediteste.databinding.EventListFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventListFragment : Fragment() {

    private lateinit var binding: EventListFragmentBinding
    private lateinit var adapter: EventListAdapter
    private val eventViewModel: EventListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EventListFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewState()
        setupAdapter()
        eventViewModel.eventList()
    }


    private fun observeViewState() {
        eventViewModel.viewStateLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is EventListViewState.Loading -> handleProgressBar(isLoading = true)
                is EventListViewState.Success -> handleSuccessState(state.eventList)
                is EventListViewState.ConnectionError -> {}
                is EventListViewState.GenericError -> {}
            }
        }
    }

    private fun setupAdapter() {
        adapter = EventListAdapter { eventId -> navigateToEventDetails(eventId) }
        binding.eventList.adapter = adapter
    }

    private fun navigateToEventDetails(eventId: String) {
        val action = EventListFragmentDirections.actionToEventDetails(eventId)
        findNavController().navigate(action)

    }

    private fun handleSuccessState(eventList: List<SimplifiedEvent>) {
        handleProgressBar(isLoading = false)
        adapter.submitList(eventList)
    }

    private fun handleProgressBar(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }


}