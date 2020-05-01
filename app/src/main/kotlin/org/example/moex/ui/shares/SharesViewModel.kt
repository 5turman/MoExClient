package org.example.moex.ui.shares

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.example.moex.core.ResourceText
import org.example.moex.core.commands.Command
import org.example.moex.core.commands.CommandLiveData
import org.example.moex.core.getMessage
import org.example.moex.data.SharesRepository
import org.example.moex.data.model.Share

/**
 * Created by 5turman on 22.03.2017.
 */

class SharesViewModel constructor(
    private val repo: SharesRepository
) : ViewModel() {

    private val refreshingLiveData = MutableLiveData<Boolean>()
    private val filteredSharesLiveData = MutableLiveData<List<Share>>()
    private val commandLiveData = CommandLiveData()

    private val sharesLiveData = MutableLiveData<List<Share>>()
    private val queryLiveData = MutableLiveData<String>("")

    init {
        viewModelScope.launch {
            sharesLiveData.asFlow().combine(
                queryLiveData.asFlow()
                    .debounce(400)
                    .map { it.trim() }
                    .distinctUntilChanged()
            ) { shares, query ->
                if (query.isEmpty()) {
                    shares
                } else {
                    shares.filter { share ->
                        share.id.contains(query, true) || share.shortName.contains(query, true)
                    }
                }
            }.collect {
                filteredSharesLiveData.value = it
            }
        }
    }

    private var loadSharesJob: Job? = null

    fun refreshing(): LiveData<Boolean> = refreshingLiveData

    fun shares(): LiveData<List<Share>> {
        if (loadSharesJob == null) {
            loadShares(false)
        }
        return filteredSharesLiveData
    }

    fun commands(): LiveData<Command> = commandLiveData

    fun onRefresh() {
        loadSharesJob?.cancel()
        loadShares(true)
    }

    fun onQueryTextChange(query: String) {
        queryLiveData.value = query
    }

    fun onShareClicked(share: Share) {
        commandLiveData.add(NavigateToShareScreen(share))
    }

    private fun loadShares(forceUpdate: Boolean) {
        refreshingLiveData.value = true

        loadSharesJob = viewModelScope.launch {
            try {
                val shares = repo.getAll(forceUpdate).sortedBy { it.shortName }
                sharesLiveData.value = shares
            } catch (e: Throwable) {
                commandLiveData.add(ShowError { getMessage(e) })
            } finally {
                refreshingLiveData.value = false
            }
        }
    }

}

data class ShowError(val error: ResourceText) : Command()

data class NavigateToShareScreen(val share: Share) : Command()
