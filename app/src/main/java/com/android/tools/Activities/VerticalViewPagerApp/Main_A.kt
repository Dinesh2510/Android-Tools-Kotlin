package com.android.tools.Activities.VerticalViewPagerApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.tools.R

import android.view.View


class Main_A : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        initSwipePager();
    }

    private fun initSwipePager() {
        val verticalViewPager: VerticalViewPager =
            findViewById<View>(R.id.vPager) as VerticalViewPager
        verticalViewPager.setAdapter(VerticlePagerAdapter(this))
    }
}