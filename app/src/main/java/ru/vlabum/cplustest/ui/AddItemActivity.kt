package ru.vlabum.cplustest.ui

import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.vlabum.cplustest.presenter.MainPresenter
import ru.vlabum.cplustest.view.IMainView

class AddItemActivity : MvpAppCompatActivity(), IMainView {

    @InjectPresenter
    lateinit var mainPresenter: MainPresenter

    @ProvidePresenter
    fun providePresenter(): MainPresenter {
        mainPresenter = MainPresenter(AndroidSchedulers.mainThread())
        return mainPresenter
    }

    override fun init() {
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}