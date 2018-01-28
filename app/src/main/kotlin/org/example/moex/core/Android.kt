package org.example.moex.core

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

/**
 * Created by 5turman on 22.03.2017.
 */
fun ViewGroup.inflate(resource: Int, attachToRoot: Boolean = false): View =
        LayoutInflater.from(this.context).inflate(resource, this, attachToRoot)

fun Context.showToast(text: CharSequence) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun View.showOrHide(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.setInvisible() {
    visibility = View.INVISIBLE
}

fun DisplayMetrics.toPx(dp: Int) = (density * dp + 0.5f).toInt()

fun Resources.toPx(dp: Int) = displayMetrics.toPx(dp)
fun Resources.toFPx(dp: Int) = displayMetrics.density * dp
