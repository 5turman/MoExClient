package org.example.moex.ui.chart

import org.example.moex.data.model.Share

/**
 * Created by 5turman on 6/2/2017.
 */
interface ChartContract {

    interface View {
        fun add(tick: Share)
    }

    interface Presenter : LifeCycle {
        fun attachView(view: View)
        fun detachView()
    }

}
