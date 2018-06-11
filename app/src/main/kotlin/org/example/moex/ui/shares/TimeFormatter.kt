package org.example.moex.ui.shares

import android.content.Context
import android.text.format.DateUtils
import org.example.moex.R
import org.joda.time.DateTime

/**
 * Created by 5turman on 04.04.2017.
 */
object TimeFormatter {

    fun format(context: Context, time: Long): CharSequence =
        when {
            isToday(time) -> DateUtils.formatDateTime(
                context,
                time,
                DateUtils.FORMAT_SHOW_TIME
            )
            isYesterday(time) -> context.getString(R.string.yesterday)
            else -> DateUtils.formatDateTime(context, time, DateUtils.FORMAT_SHOW_DATE)
        }

    private fun isToday(time: Long): Boolean =
        DateTime.now().withTimeAtStartOfDay().isBefore(time)

    private fun isYesterday(time: Long): Boolean =
        DateTime.now().minusDays(1).withTimeAtStartOfDay().isBefore(time)

}
