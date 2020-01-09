package org.example.moex.core

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

inline fun <T> Fragment.bind(data: LiveData<T>, crossinline observer: (T) -> Unit) {
    data.observe(viewLifecycleOwner, Observer { observer(it) })
}
