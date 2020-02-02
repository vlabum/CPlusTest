package ru.vlabum.cplustest.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import layout.ItemsRVAdapter
import ru.vlabum.cplustest.App
import ru.vlabum.cplustest.R
import ru.vlabum.cplustest.model.entity.Item
import ru.vlabum.cplustest.presenter.MainPresenter
import ru.vlabum.cplustest.service.RoomSaverResultReceiver
import ru.vlabum.cplustest.view.IMainView


class MainActivity : MvpAppCompatActivity(), IMainView, RoomSaverResultReceiver.Receiver {

    lateinit var rvAdapter: ItemsRVAdapter

    private val PERMISSION_RESULT_CODE = 1200

    @InjectPresenter
    lateinit var mainPresenter: MainPresenter

    @ProvidePresenter
    fun providePresenter(): MainPresenter {
        mainPresenter = MainPresenter(AndroidSchedulers.mainThread())
        App.getInstance().getAppComponent().inject(mainPresenter)
        return mainPresenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        App.getInstance().resultReceiver.setReceiver(this) //WeakReference
        mainPresenter.onPermission(true)

        fab.setOnClickListener {
            startAddItemActivity()
        }

        if (Build.VERSION.SDK_INT >= 23) {
            if (
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    PERMISSION_RESULT_CODE
                )
            }
        }
    }

    fun startAddItemActivity() {
        val intent = Intent(this, AddItemActivity::class.java)
        startActivity(intent)
    }

    override fun openDetails(position: Int) {
        val list: ArrayList<Item> = mainPresenter.listItems.items as ArrayList<Item>
        val intent = Intent(this, ScreenSlidePagerActivity::class.java)
            .apply {
                putParcelableArrayListExtra(Item.ARGS_BUNDLE_LIST, list)
                putExtra("position", position)
            }
        startActivity(intent)
    }

    override fun init() {
        rv_items.layoutManager = LinearLayoutManager(this)
        rvAdapter = ItemsRVAdapter(mainPresenter.listItems)
        rv_items.adapter = rvAdapter
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        rl_loading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        rl_loading.visibility = View.GONE
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_RESULT_CODE -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                ) {
                    mainPresenter.onPermission(true)
                } else {
                    mainPresenter.onPermission(false)
                }
            }
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

    override fun onReceiveResult(resultCode: Int, data: Bundle?) {
        if (resultCode == RoomSaverResultReceiver.RESULT_CODE_OK)
            mainPresenter.onRestart()
        showMessage(resultCode.toString())
    }

    override fun notifyRV() {
        rvAdapter.notifyDataSetChanged()
    }
}
