package org.example.moex.ui.chart

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import org.example.moex.data.SharesRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by 5turman on 6/2/2017.
 */
class ChartPresenter @Inject constructor(
    val shareId: String,
    val repo: SharesRepository
) : ChartContract.Presenter {

    private var view: ChartContract.View? = null

    private var disposable: Disposable? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        println("onStart")
        disposable = repo.get(shareId)
            .retryWhen { errors ->
                errors.flatMap({ error ->
                    Observable.timer(5, TimeUnit.SECONDS)
                })
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { share ->
                view?.add(share)
            }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        println("onStop")
        disposable?.dispose()
    }

    override fun attachView(view: ChartContract.View) {
        println("attachView")
        this.view = view
    }

    override fun detachView() {
        println("detachView")
        this.view = null
    }

}
