package org.example.moex.ui.shares

import android.content.Context
import android.text.format.DateUtils
import org.example.moex.R
import org.example.moex.di.scope.PerApp
import org.joda.time.DateTime
import javax.inject.Inject

/**
 * Created by 5turman on 04.04.2017.
 */
@PerApp
class TimeFormatter @Inject constructor(val context: Context) {

    fun format(time: Long): CharSequence {
        val result: String

        val now = DateTime.now()

        if (now.withTimeAtStartOfDay().isBefore(time)) { // today or in future

            result = DateUtils.formatDateTime(context, time, DateUtils.FORMAT_SHOW_TIME)

        } else if (now.minusDays(1).withTimeAtStartOfDay().isBefore(time)) { // yesterday

            result = context.getString(R.string.yesterday)

        } else {

            result = DateUtils.formatDateTime(context, time, DateUtils.FORMAT_SHOW_DATE)
        }

        return result
    }

}
