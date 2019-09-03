package org.example.moex.core

import android.content.Context
import org.example.moex.R
import org.example.moex.api.NetworkException

fun Context.getMessage(throwable: Throwable): String =
    getString(if (throwable === NetworkException) R.string.no_connection else R.string.error_receiving_data)
