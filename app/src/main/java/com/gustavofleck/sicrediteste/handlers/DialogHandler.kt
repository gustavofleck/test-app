package com.gustavofleck.sicrediteste.handlers

import android.content.Context
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.gustavofleck.sicrediteste.R
import com.gustavofleck.sicrediteste.enums.ErrorsEnum
import com.gustavofleck.sicrediteste.enums.ErrorsEnum.*

class DialogHandler {

    fun showErrorDialog(error: ErrorsEnum, context: Context) {
        when (error) {
            GENERIC_ERROR ->
                createErrorDialogContent(context, R.string.generic_error_message).show()
            CONNECTION_ERROR ->
                createErrorDialogContent(context, R.string.connection_error_message).show()
            INVALID_DATA_ERROR ->
                createErrorDialogContent(context, R.string.invalid_data_error_message).show()
        }
    }

    private fun createErrorDialogContent(context: Context, @StringRes messageRes: Int): AlertDialog {
        return AlertDialog.Builder(context).setTitle(R.string.error_ocurred).setMessage(messageRes)
            .setNeutralButton(R.string.next) { dialog, _ -> dialog.dismiss() }.create()
    }

}