package ru.vlabum.cplustest.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import ru.vlabum.cplustest.R
import ru.vlabum.cplustest.model.entity.Item

class ScreenSlidePagerActivity : FragmentActivity() {

    private lateinit var viewPager: ViewPager2

    var localList: List<Item>? = null
    var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        localList = intent.getParcelableArrayListExtra(Item.ARGS_BUNDLE_LIST)
        position = intent.getIntExtra("position", 0)

        localList?.let { } ?: run {
            localList = arrayListOf(Item("Пустой список", ""))
            position = 0
        }

        setContentView(R.layout.activity_screen_slide)

        viewPager = findViewById(R.id.pager)

        val pagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = pagerAdapter
        viewPager.setCurrentItem(position, false)
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) :
        FragmentStateAdapter(fa) {
        override fun getItemCount(): Int {
            return localList!!.size
        }

        override fun createFragment(position: Int): Fragment {
            return ScreenSlidePageFragment.create(localList!![position])
        }
    }


}