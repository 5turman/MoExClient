package org.example.moex.ui.shares

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution
import org.example.moex.data.model.Share

/**
 * Created by 5turman on 22.03.2017.
 */
interface SharesContract {

    interface View : MvpView {
        @AddToEndSingle
        fun showRefreshing(show: Boolean)

        @AddToEndSingle
        fun showShares(shares: List<Share>)

        @OneExecution
        fun showError(error: String)

        @OneExecution
        fun show(share: Share)
    }

    interface Presenter {
        fun onRefresh()
        fun onQueryTextChange(query: String)
        fun onShareClick(share: Share)
    }

}
