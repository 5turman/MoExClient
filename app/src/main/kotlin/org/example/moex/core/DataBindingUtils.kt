package org.example.moex.core

import androidx.databinding.BindingConversion

@BindingConversion
fun convertDoubleToString(value: Double): String = value.toString()
