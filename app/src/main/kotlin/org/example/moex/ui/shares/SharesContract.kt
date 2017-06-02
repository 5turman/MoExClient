package org.example.moex.ui.shares

import org.example.moex.core.mvp.BasePresenter
import org.example.moex.core.mvp.BaseView
import org.example.moex.data.model.Share

/**
 * Created by 5turman on 22.03.2017.
 */
interface SharesContract {

    interface View : BaseView {
        fun showRefreshing()
        fun hideRefreshing()
        fun showShares(shares: List<Share>)
        fun showError(error: String)
        fun show(share: Share)
    }

    interface Presenter : BasePresenter<View> {
        fun loadShares()
        fun refresh()
        fun onQueryTextChange(query: String)
        fun show(share: Share)
    }

}
