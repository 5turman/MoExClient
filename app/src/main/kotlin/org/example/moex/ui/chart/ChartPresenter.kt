package org.example.moex.ui.chart

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import org.example.moex.data.SharesRepository
import java.util.concurrent.TimeUnit

/**
 * Created by 5turman on 6/2/2017.
 */
class ChartPresenter constructor(
    val shareId: String,
    val repo: SharesRepository
) : ChartContract.Presenter {

    private var view: ChartContract.View? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        println("onStart")
//        disposable = repo.get(shareId)
//            .retryWhen { errors ->
//                errors.flatMap {
//                    Observable.timer(5, TimeUnit.SECONDS)
//                }
//            }
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe { share ->
//                view?.add(share)
//            }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        println("onStop")
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
