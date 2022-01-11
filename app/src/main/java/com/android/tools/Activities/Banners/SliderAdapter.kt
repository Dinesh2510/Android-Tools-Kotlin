package com.android.tools.Activities.Banners

import android.app.Activity
import com.android.tools.utils.OnClick
import androidx.viewpager.widget.PagerAdapter
import android.view.LayoutInflater
import android.annotation.SuppressLint
import android.view.ViewGroup
import com.android.tools.R
import com.google.android.material.textview.MaterialTextView
import com.bumptech.glide.Glide
import com.android.tools.utils.EnchantedViewPager
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.android.tools.utils.Method
import java.lang.Exception

class SliderAdapter(
    private val activity: Activity,
    private val type: String,
    private val sliderLists: MutableList<Banner>,
    onClick: OnClick?) : PagerAdapter() {
    private val method: Method
    private val inflater: LayoutInflater

    @SuppressLint("SetTextI18n")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view: View
        Log.e("type", "instantiateItem: "+type )
        if (type == "external") {
            view = inflater.inflate(R.layout.slider_adapter, container, false)
            var ImageUrl = "http://pixeldev.in/webservices/e_commerce/admin/"

            val cardView: CardView = view.findViewById(R.id.cardView_slider_adapter)
            val imageView = view.findViewById<ImageView>(R.id.imageView_slider_adapter)
            val textViewTitle: MaterialTextView =
                view.findViewById(R.id.textView_title_slider_adapter)
            val textViewSubTitle: MaterialTextView =
                view.findViewById(R.id.textView_subTitle_slider_adapter)
            Glide.with(activity).load(ImageUrl + sliderLists[position].img)
                .placeholder(R.drawable.ic_appicon).into(imageView)
            textViewTitle.text = sliderLists[position].id
            cardView.setOnClickListener { v: View? ->
                method.click(
                    position,
                    type,
                    sliderLists[position].id,
                    sliderLists[position].id
                )
            }
            cardView.tag = EnchantedViewPager.ENCHANTED_VIEWPAGER_POSITION + position
        } else {
            var ImageUrl = "http://pixeldev.in/webservices/e_commerce/admin/"

            view = inflater.inflate(R.layout.slider_external_adapter, container, false)
            val cardView: CardView =
                view.findViewById(R.id.cardView_slider_external_adapter)
            val imageView = view.findViewById<ImageView>(R.id.imageView_slider_external_adapter)
            val textViewTitle: MaterialTextView =
                view.findViewById(R.id.textView_slider_external_adapter)
            Glide.with(activity).load(ImageUrl + sliderLists[position].img)
                .placeholder(R.drawable.ic_appicon).into(imageView)
            textViewTitle.text = sliderLists[position].id
            cardView.setOnClickListener { v: View? ->
                try {
                    activity.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(sliderLists[position].id)
                        )
                    )
                } catch (e: Exception) {
                    method.alertBox(activity.resources.getString(R.string.wrong))
                }
            }
            cardView.tag = EnchantedViewPager.ENCHANTED_VIEWPAGER_POSITION + position
        }
        container.addView(view, 0)
        return view
    }

    override fun getCount(): Int {
        return sliderLists.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    init {

        // TODO Auto-generated constructor stub
        inflater = activity.layoutInflater
        method = Method(activity, onClick)
    }
}