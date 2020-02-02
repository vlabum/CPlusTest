package ru.vlabum.cplustest.view

import android.content.Intent
import com.arellomobile.mvp.MvpView

interface IAddItemView : MvpView {
    fun init()
    fun saveItem(name: String, description: String?, imagePath: String?)
    fun showMessage(message: String)
}