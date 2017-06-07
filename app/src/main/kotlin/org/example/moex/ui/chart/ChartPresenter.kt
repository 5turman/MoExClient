package org.example.moex.ui.chart

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import org.example.moex.data.SharesRepository
import javax.inject.Inject

/**
 * Created by 5turman on 6/2/2017.
 */
class ChartPresenter @Inject constructor(
        val shareId: String,
        val repo: SharesRepository) : ChartContract.Presenter {

    private var view: ChartContract.View? = null

    private var disposable: Disposable? = null

    override fun onStart() {
        println("onStart")
        disposable = repo.get(shareId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { share ->
                    view?.add(share)
                }
    }

    override fun onStop() {
        println("onStop")
        disposable?.dispose()
        // stop gathering data
    }

    override fun attachView(view: ChartContract.View) {
        println("attachView")
        this.view = view
    }

    override fun detachView() {
        println("detachView")
        this.view = null
    }

    /*
2. draw simple chart. While just points
 */

}