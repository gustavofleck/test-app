package com.gustavofleck.sicrediteste.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.gustavofleck.domain.models.Event
import com.gustavofleck.presentation.viewmodels.EventManagementViewModel
import com.gustavofleck.presentation.viewstates.EventManagementViewState
import com.gustavofleck.sicrediteste.R
import com.gustavofleck.sicrediteste.databinding.EventManagementFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventManagementFragment : Fragment() {

    private lateinit var binding: EventManagementFragmentBinding
    private val viewModel: EventManagementViewModel by viewModel()
    private val args: EventManagementFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EventManagementFragmentBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewState()
        viewModel.eventDetails(args.eventId)
    }

    private fun observeViewState() {
        viewModel.viewStateLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is EventManagementViewState.Loading -> handleProgressBar(isLoading = true)
                is EventManagementViewState.SuccessDetails -> handleSuccessDetailsState(state.event)
                is EventManagementViewState.ConnectionError -> TODO()
                is EventManagementViewState.GenericError -> TODO()
                is EventManagementViewState.SuccessCheckIn -> TODO()
            }
        }
    }

    private fun handleSuccessDetailsState(event: Event) {
        handleProgressBar(isLoading = false)
        setupShareActionButton(event)
        setupCheckInActionButton()
        with(binding) {
            eventDetailTitle.text = event.title
            eventDetailsDescription.text = event.description
            eventDetailDate.text = getString(R.string.event_date_hour, event.date)
            eventDetailPrice.text = getString(R.string.event_price, event.price)
            setupEventImage(event.image)
        }
    }

    private fun setupEventImage(image: String) {
        Glide.with(requireContext()).load(image).centerCrop()
            .placeholder(R.drawable.ic_image_not_found).into(binding.eventDetailImage)
    }

    private fun handleProgressBar(isLoading: Boolean) {
        with(binding) {
            eventDetailsProgressBar.isVisible = isLoading
            eventDetailsContent.isVisible = isLoading.not()
        }
    }

    private fun setupCheckInActionButton() {
        binding.eventCheckInButton.setOnClickListener {

        }
    }

    private fun setupShareActionButton(event: Event) {
        binding.eventShareButton.setOnClickListener {
            val sendIntent = createShareContent(event)
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    private fun createShareContent(event: Event) = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            getString(R.string.event_share_text, event.date, event.title, event.price)
        )
        type = "text/plain"
    }
}