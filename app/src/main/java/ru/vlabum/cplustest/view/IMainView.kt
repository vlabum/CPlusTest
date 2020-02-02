package ru.vlabum.cplustest.view

import com.arellomobile.mvp.MvpView

interface IMainView : MvpView {
    fun init()

    fun showLoading()
    fun hideLoading()

    fun notifyRV()
    fun openDetails(position: Int)

    fun showMessage(message: String)

}