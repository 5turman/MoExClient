package org.example.moex.ui.shares

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import org.example.moex.data.model.Share

/**
 * Created by 5turman on 22.03.2017.
 */
interface SharesContract {

    @StateStrategyType(AddToEndSingleStrategy::class)
    interface View : MvpView {
        fun showRefreshing(show: Boolean)

        fun showShares(shares: List<Share>)

        @StateStrategyType(OneExecutionStateStrategy::class)
        fun showError(error: String)

        @StateStrategyType(OneExecutionStateStrategy::class)
        fun show(share: Share)
    }

    interface Presenter {
        fun onRefresh()
        fun onQueryTextChange(query: String)
        fun onShareClick(share: Share)
    }

}
