package org.example.moex.ui.shares

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.example.moex.BuildConfig
import org.example.moex.core.ResourceText
import org.example.moex.core.commands.Command
import org.example.moex.core.commands.CommandLiveData
import org.example.moex.core.getMessage
import org.example.moex.data.SharesRepository
import org.example.moex.data.model.Share
import java.util.concurrent.TimeUnit

/**
 * Created by 5turman on 22.03.2017.
 */
class SharesViewModel constructor(
    private val repo: SharesRepository
) : ViewModel() {

    private val refreshingLiveData = MutableLiveData<Boolean>()
    private val sharesLiveData = MutableLiveData<List<Share>>()
    private val commandLiveData = CommandLiveData()

    private val sharesSubject = BehaviorSubject.create<List<Share>>()
    private val querySubject = BehaviorSubject.createDefault<String>("")

    private val queryDisposable = Observable.combineLatest(
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
            sharesLiveData.value = it
        }

    private var loadSharesJob: Job? = null

    fun refreshing(): LiveData<Boolean> = refreshingLiveData

    fun shares(): LiveData<List<Share>> {
        if (loadSharesJob == null) {
            loadShares(false)
        }
        return sharesLiveData
    }

    fun commands(): LiveData<Command> = commandLiveData

    fun onRefresh() {
        loadSharesJob?.cancel()
        loadShares(true)
    }

    fun onQueryTextChange(query: String) {
        querySubject.onNext(query)
    }

    fun onShareClicked(share: Share) {
        commandLiveData.add(NavigateToShareScreen(share))
    }

    private fun loadShares(forceUpdate: Boolean) {
        refreshingLiveData.value = true

        loadSharesJob = viewModelScope.launch {
            try {
                val shares = repo.getAll(forceUpdate).sortedBy { it.shortName }
                sharesSubject.onNext(shares)
            } catch (e: Throwable) {
                commandLiveData.add(ShowError { getMessage(e) })
            } finally {
                refreshingLiveData.value = false
            }
        }
    }

    override fun onCleared() {
        queryDisposable.dispose()
    }
}

data class ShowError(val error: ResourceText) : Command()

data class NavigateToShareScreen(val share: Share) : Command()
