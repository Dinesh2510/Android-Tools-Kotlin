package com.android.tools.Activities.VerticalViewPagerApp

import android.content.Context
import android.view.MotionEvent

import android.opengl.ETC1.getHeight

import android.opengl.ETC1.getWidth
import android.util.AttributeSet
import android.view.View

import androidx.viewpager.widget.ViewPager


class VerticalViewPager : ViewPager {
    constructor(context: Context?) : super(context!!) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        init()
    }

    private fun init() {
        // The majority of the magic happens here
        setPageTransformer(true, VerticalPageTransformer())
        // The easiest way to get rid of the overscroll drawing that happens on the left and right
        overScrollMode = OVER_SCROLL_NEVER
    }

    private inner class VerticalPageTransformer : PageTransformer {
        private val MIN_SCALE = 0.75f

        override fun transformPage(view: View, position: Float) {
            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0F)
            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1F)
                // Counteract the default slide transition
                view.setTranslationX(view.getWidth() * -position)

                //set Y position to swipe in from top
                val yPosition: Float = position * view.getHeight()
                view.setTranslationY(yPosition)
                view.setScaleX(1F)
                view.setScaleY(1F)
            } else if (position <= 1) { // [0,1]
                view.setAlpha(1F)

                // Counteract the default slide transition
                view.setTranslationX(view.getWidth() * -position)


                // Scale the page down (between MIN_SCALE and 1)
                val scaleFactor = (MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position)))
                view.setScaleX(scaleFactor)
                view.setScaleY(scaleFactor)
            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0F)
            }
        }


    }

    /**
     * Swaps the X and Y coordinates of your touch event.
     */
    private fun swapXY(ev: MotionEvent): MotionEvent {
        val width = width.toFloat()
        val height = height.toFloat()
        val newX = ev.y / height * width
        val newY = ev.x / width * height
        ev.setLocation(newX, newY)
        return ev
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val intercepted = super.onInterceptTouchEvent(swapXY(ev))
        swapXY(ev) // return touch coordinates to original reference frame for any child views
        return intercepted
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return super.onTouchEvent(swapXY(ev))
    }
}