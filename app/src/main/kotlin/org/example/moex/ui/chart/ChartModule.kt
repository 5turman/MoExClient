package org.example.moex.ui.chart

import dagger.Module
import dagger.Provides

/**
 * Created by 5turman on 6/2/2017.
 */
@Module
class ChartModule(val shareId: String) {

    @Provides
    fun shareId() = shareId

    @Provides
    fun chartPresenter(presenter: ChartPresenter): ChartContract.Presenter = presenter

}
