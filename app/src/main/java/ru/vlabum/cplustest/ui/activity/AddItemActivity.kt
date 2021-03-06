package ru.vlabum.cplustest.ui.activity

import android.app.Activity
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_add_item.*
import kotlinx.android.synthetic.main.activity_main.*
import ru.vlabum.cplustest.App
import ru.vlabum.cplustest.R
import ru.vlabum.cplustest.extensions.getPath
import ru.vlabum.cplustest.presenter.AddItemPresenter
import ru.vlabum.cplustest.service.RoomSaverService
import ru.vlabum.cplustest.ui.util.setImage
import ru.vlabum.cplustest.view.IAddItemView


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

        et_add_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                addItemPresenter.onNameTyped(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        et_add_description.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                addItemPresenter.onDescriptionTyped(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

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

    override fun onBackPressed() {
        addItemPresenter.needSave()
        super.onBackPressed()
    }

//    override fun onStop() {
//        if (!isLoadingPhoto)
//            addItemPresenter.needSave()
//        super.onStop()
//    }

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

    override fun reloadImage(path: String) {
        setImage(path, iv_add_icon)
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
                } else {
                    val source = ImageDecoder.createSource(this.contentResolver, it)
                    val bitmap = ImageDecoder.decodeBitmap(source)
                    iv_add_icon.setImageBitmap(bitmap)
                }
                addItemPresenter.onImageSelected(selectedImage.getPath(this.applicationContext))
            }
        }
        isLoadingPhoto = false
    }
}