package ru.vlabum.cplustest.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.vlabum.cplustest.model.entity.Item
import ru.vlabum.cplustest.ui.adapter.PageView

class ScreenSlidePageFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val pageView = PageView(layoutInflater, container)
        pageView.bind(Item.fromBundle(arguments!!))
        return pageView.view
    }

    companion object {
        fun create(item: Item): ScreenSlidePageFragment {
            val fragment = ScreenSlidePageFragment()
            fragment.arguments = item.toBundle()
            return fragment
        }
    }
}