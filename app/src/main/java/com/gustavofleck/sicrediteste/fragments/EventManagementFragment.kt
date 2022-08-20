package com.gustavofleck.sicrediteste.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.gustavofleck.domain.models.Event
import com.gustavofleck.presentation.viewmodels.EventManagementViewModel
import com.gustavofleck.presentation.viewstates.EventManagementViewState
import com.gustavofleck.sicrediteste.R
import com.gustavofleck.sicrediteste.bottomsheets.CheckInBottomSheet
import com.gustavofleck.sicrediteste.databinding.EventManagementFragmentBinding
import com.gustavofleck.sicrediteste.enums.ErrorsEnum.GENERIC_ERROR
import com.gustavofleck.sicrediteste.enums.ErrorsEnum.CONNECTION_ERROR
import com.gustavofleck.sicrediteste.enums.ErrorsEnum.INVALID_DATA_ERROR
import com.gustavofleck.sicrediteste.handlers.DialogHandler
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventManagementFragment : Fragment() {

    private lateinit var binding: EventManagementFragmentBinding
    private val dialogHandler: DialogHandler by inject()
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
                is EventManagementViewState.SuccessCheckIn -> handleSuccessCheckInState()
                is EventManagementViewState.ConnectionError -> handleConnectionErrorState()
                is EventManagementViewState.GenericError -> handleGenericErrorState()
                is EventManagementViewState.InvalidDataError -> handleInvalidDataErrorState()
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

    private fun handleSuccessCheckInState() {
        handleProgressBar(isLoading = false)
        AlertDialog.Builder(requireContext()).setTitle(R.string.success_check_in)
            .setNeutralButton(R.string.next) { dialog, _ -> dialog.dismiss() }.create().show()
    }

    private fun handleConnectionErrorState() {
        handleProgressBar(isLoading = false)
        dialogHandler.showErrorDialog(CONNECTION_ERROR, requireContext())
    }

    private fun handleGenericErrorState() {
        handleProgressBar(isLoading = false)
        dialogHandler.showErrorDialog(GENERIC_ERROR, requireContext())
    }

    private fun handleInvalidDataErrorState() {
        handleProgressBar(isLoading = false)
        dialogHandler.showErrorDialog(INVALID_DATA_ERROR, requireContext())
    }

    private fun handleProgressBar(isLoading: Boolean) {
        with(binding) {
            eventDetailsProgressBar.isVisible = isLoading
            eventDetailsContent.isVisible = isLoading.not()
        }
    }

    private fun setupCheckInActionButton() {
        binding.eventCheckInButton.setOnClickListener {
            CheckInBottomSheet(::checkInAction).show(parentFragmentManager, javaClass.name)
        }
    }

    private fun checkInAction(name: String, email: String) {
        viewModel.eventCheckIn(args.eventId, name, email)
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
    }
}