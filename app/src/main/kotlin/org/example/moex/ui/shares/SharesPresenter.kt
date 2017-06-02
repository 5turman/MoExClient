package org.example.moex.ui.shares

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import org.example.moex.BuildConfig
import org.example.moex.data.SharesRepository
import org.example.moex.data.model.Share
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by 5turman on 22.03.2017.
 */
class SharesPresenter @Inject constructor(private val repo: SharesRepository) : SharesContract.Presenter {

    private val sharesSubject = PublishSubject.create<List<Share>>()
    private val querySubject = BehaviorSubject.createDefault<String>("")

    private lateinit var modelDisposable: Disposable

    private var view: SharesContract.View? = null
    private var repoDisposable: Disposable? = null

    override fun attachView(view: SharesContract.View) {
        this.view = view

        modelDisposable = Observable.combineLatest(
                sharesSubject,
                querySubject
                        .apply {
                            // Temporary workaround to pass UI tests
                            if (BuildConfig.FLAVOR != "mock") {
                                debounce(400, TimeUnit.MILLISECONDS) // computation scheduler
                            }
                        }
                        .map(String::trim)
                        .distinctUntilChanged(),
                BiFunction<List<Share>, String, List<Share>> { shares, query ->
                    if (query.isEmpty()) {
                        shares
                    } else {
                        shares.filter { share ->
                            share.id.contains(query, true) || share.shortName.contains(query, true)
                        }
                    }
                }
        )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    this@SharesPresenter.view?.showShares(it)
                }
    }

    override fun detachView() {
        repoDisposable?.dispose()
        modelDisposable.dispose()
        view = null
    }

    override fun loadShares() {
        loadShares(false)
    }

    override fun refresh() {
        repoDisposable?.dispose()
        loadShares(true)
    }

    override fun onQueryTextChange(query: String) {
        querySubject.onNext(query)
    }

    override fun show(share: Share) {
        view?.show(share)
    }

    private fun loadShares(forceUpdate: Boolean) {
        view?.showRefreshing()

        repoDisposable = repo.getAll(forceUpdate)
                .map { it.sortedBy { it.shortName } }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate {
                    view?.hideRefreshing()
                }
                .subscribe(
                        { shares -> sharesSubject.onNext(shares) },
                        { error ->
                            val message = error.message ?: error.javaClass.simpleName
                            this@SharesPresenter.view?.showError(message)
                        }
                )
    }

}
