package org.example.moex.api

import java.io.IOException

/**
 * Created by 5turman on 14.04.2017.
 */
inline fun <T> tryWithApiErrorHandler(action: () -> T): T =
    try {
        action()
    } catch (e: IOException) {
        throw NetworkException
    }
