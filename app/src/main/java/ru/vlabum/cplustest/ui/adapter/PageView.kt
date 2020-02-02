package ru.vlabum.cplustest.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ru.vlabum.cplustest.R
import ru.vlabum.cplustest.model.entity.Item
import ru.vlabum.cplustest.ui.util.setImage

class PageView(layoutInflater: LayoutInflater, container: ViewGroup?) {

    val view: View = layoutInflater.inflate(R.layout.fragment_screen_slide_page, container, false)

    private var imageView: ImageView
    private var tvName: TextView
    private var tvDescr: TextView

    init {
        imageView = view.findViewById(R.id.iv_slide_image)
        tvName = view.findViewById(R.id.tv_slide_name)
        tvDescr = view.findViewById(R.id.tv_slide_description)
    }

    fun bind(item: Item) {
        tvName.text = item.getName()
        tvDescr.text = item.getDescription()
        item.getImagePath()?.let {
            setImage(item.getImagePath()!!, imageView)
        }
    }
}
