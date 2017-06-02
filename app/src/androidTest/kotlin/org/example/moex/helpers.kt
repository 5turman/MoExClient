package org.example.moex

import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Configuration

/**
 * Created by 5turman on 21.04.2017.
 */
fun Activity.rotateOrientation() {
    requestedOrientation =
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            else
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
}
