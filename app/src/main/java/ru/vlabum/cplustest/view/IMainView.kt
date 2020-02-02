package ru.vlabum.cplustest.view

import com.arellomobile.mvp.MvpView

interface IMainView : MvpView {
    fun init()

    fun showLoading()
    fun hideLoading()

    fun notifyRV()

    fun showMessage(message: String)

}