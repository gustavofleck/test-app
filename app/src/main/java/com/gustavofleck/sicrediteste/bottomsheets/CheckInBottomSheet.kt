package com.gustavofleck.sicrediteste.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.gustavofleck.sicrediteste.databinding.CheckInBottomSheetBinding

class CheckInBottomSheet(
    private val checkInAction: (String, String) -> Unit
): BottomSheetDialogFragment() {

    private lateinit var binding: CheckInBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CheckInBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCheckInConfirmationButton()
    }

    private fun setupCheckInConfirmationButton() {
        with(binding) {
            checkInConfirmation.setOnClickListener {
                checkInAction.invoke(nameInputText.text.toString(), emailInputText.text.toString())
                dismiss()
            }
        }
    }
}