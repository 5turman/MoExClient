package org.example.moex.api

import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.SingleTransformer
import org.example.moex.di.scope.PerApp
import java.io.IOException
import javax.inject.Inject

/**
 * Created by 5turman on 14.04.2017.
 */
@PerApp
class ApiErrorHandler @Inject constructor() : SingleTransformer<Any, Any> {

    override fun apply(upstream: Single<Any>): SingleSource<Any> =
        upstream.onErrorResumeNext { error ->
            Single.error<Any>(
                if (error is IOException) NetworkException else error
            )
        }

    @Suppress("UNCHECKED_CAST")
    fun <T> cast(): SingleTransformer<T, T> = (this as SingleTransformer<T, T>)

}
