package org.example.moex.core.mvp

/**
 * Created by 5turman on 22.03.2017.
 */
interface BasePresenter<in TView : BaseView> {
    fun attachView(view: TView)
    fun detachView()
}
