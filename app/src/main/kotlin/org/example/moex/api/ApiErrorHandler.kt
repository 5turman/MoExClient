package org.example.moex.api

import android.content.res.Resources
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.SingleTransformer
import org.example.moex.R
import org.example.moex.di.scope.PerApp
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * Created by 5turman on 14.04.2017.
 */
@PerApp
class ApiErrorHandler @Inject constructor(private val res: Resources) : SingleTransformer<Any, Any> {

    override fun apply(upstream: Single<Any>): SingleSource<Any> =
            upstream.onErrorResumeNext { error ->
                Single.error<Any>(
                        if (error is UnknownHostException)
                            NetworkException(res.getString(R.string.error_network))
                        else error
                )
            }

    @Suppress("UNCHECKED_CAST")
    fun <T> cast(): SingleTransformer<T, T> {
        return this as SingleTransformer<T, T>
    }

}
