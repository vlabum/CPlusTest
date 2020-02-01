package ru.vlabum.cplustest.ui

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import io.reactivex.android.schedulers.AndroidSchedulers

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import layout.ItemsRVAdapter
import ru.vlabum.cplustest.R
import ru.vlabum.cplustest.presenter.MainPresenter
import ru.vlabum.cplustest.view.IMainView

class MainActivity : MvpAppCompatActivity(), IMainView {

    lateinit var rvAdapter: ItemsRVAdapter

    @InjectPresenter
    lateinit var mainPresenter: MainPresenter

    @ProvidePresenter
    fun providePresenter(): MainPresenter {
        mainPresenter = MainPresenter(AndroidSchedulers.mainThread())
        return mainPresenter
    }

    fun startAddItemActivity() {
        val intent = Intent(this, AddItemActivity::class.java)
        startActivity(intent)
    }

    override fun init() {
        rv_items.layoutManager = LinearLayoutManager(this)
        rvAdapter = ItemsRVAdapter(mainPresenter.listItems)
        rv_items.adapter = rvAdapter
    }

    override fun showLoading() {
        rl_loading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        rl_loading.visibility = View.GONE
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            startAddItemActivity()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
