package org.example.moex.ui.shares

import android.content.res.Resources
import moxy.MvpPresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import moxy.InjectViewState
import org.example.moex.BuildConfig
import org.example.moex.core.getMessage
import org.example.moex.data.SharesRepository
import org.example.moex.data.model.Share
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by 5turman on 22.03.2017.
 */
@InjectViewState
class SharesPresenter
@Inject constructor(
    private val repo: SharesRepository,
    private val res: Resources
) :
    MvpPresenter<SharesContract.View>(), SharesContract.Presenter {

    private val sharesSubject = PublishSubject.create<List<Share>>()
    private val querySubject = BehaviorSubject.createDefault<String>("")

    private lateinit var queryDisposable: Disposable

    private var repoDisposable: Disposable? = null

    override fun onFirstViewAttach() {
        queryDisposable = Observable.combineLatest(
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
                viewState.showShares(it)
            }

    }

    override fun attachView(view: SharesContract.View) {
        super.attachView(view)
        loadShares(false)
    }

    override fun detachView(view: SharesContract.View?) {
        repoDisposable?.dispose()
        super.detachView(view)
    }

    override fun onDestroy() {
        queryDisposable.dispose()
        super.onDestroy()
    }

    override fun onRefresh() {
        repoDisposable?.dispose()
        loadShares(true)
    }

    override fun onQueryTextChange(query: String) {
        querySubject.onNext(query)
    }

    override fun onShareClick(share: Share) {
        viewState.show(share)
    }

    private fun loadShares(forceUpdate: Boolean) {
        viewState.showRefreshing(true)

        repoDisposable = repo.getAll(forceUpdate)
            .map { it.sortedBy { it.shortName } }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate {
                viewState.showRefreshing(false)
            }
            .subscribe(
                { shares -> sharesSubject.onNext(shares) },
                { error ->
                    viewState.showError(res.getMessage(error))
                }
            )
    }

}
