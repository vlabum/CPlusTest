package ru.vlabum.cplustest.ui.activity

import android.app.Activity
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.bumptech.glide.Glide
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_add_item.*
import kotlinx.android.synthetic.main.activity_main.*
import ru.vlabum.cplustest.App
import ru.vlabum.cplustest.R
import ru.vlabum.cplustest.presenter.AddItemPresenter
import ru.vlabum.cplustest.service.RoomSaverService
import ru.vlabum.cplustest.view.IAddItemView
import java.io.File


class AddItemActivity : MvpAppCompatActivity(), IAddItemView, View.OnClickListener {

    val TAG = "CPLUS_AddItemActivity"

    val RESULT_LOAD_IMAGE = 1110

    var isLoadingPhoto = false


    @InjectPresenter
    lateinit var addItemPresenter: AddItemPresenter

    @ProvidePresenter
    fun providePresenter(): AddItemPresenter {
        addItemPresenter = AddItemPresenter(AndroidSchedulers.mainThread())
        return addItemPresenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
        setSupportActionBar(toolbar)
    }

    override fun init() {
        iv_add_icon.setOnClickListener(this)
    }

    override fun saveItem(name: String, description: String?, imagePath: String?) {
        val intent = Intent(this, RoomSaverService::class.java).apply {
            putExtra(App.ITEM_NAME, name)
            putExtra(App.ITEM_DESCRIPTION, description)
            putExtra(App.ITEM_IMAGE_PATH, imagePath)
        }
        startService(intent)
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onStop() {
        if (!isLoadingPhoto)
            addItemPresenter.onPaused()
        super.onStop()
    }

    override fun onClick(view: View?) {
        if (view is ImageView) {
            isLoadingPhoto = true
            val i = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(i, RESULT_LOAD_IMAGE)
            Log.d(TAG, "onClick ImageView")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && null != data) {
            val selectedImage: Uri? = data.data

            selectedImage?.let {
                if (Build.VERSION.SDK_INT < 28) {
                    val bitmap = MediaStore.Images.Media.getBitmap(
                        this.contentResolver,
                        it
                    )
                    iv_add_icon.setImageBitmap(bitmap)
                }
                else {
                    val source = ImageDecoder.createSource(this.contentResolver, it)
                    val bitmap = ImageDecoder.decodeBitmap(source)
                    iv_add_icon.setImageBitmap(bitmap)
                }
            }
        }
        isLoadingPhoto = false
    }
}