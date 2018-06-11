package org.example.moex.ui.chart

import dagger.Subcomponent

/**
 * Created by 5turman on 6/2/2017.
 */
@Subcomponent(modules = [ChartModule::class])
interface ChartComponent {

    fun inject(chart: ChartView)

}
