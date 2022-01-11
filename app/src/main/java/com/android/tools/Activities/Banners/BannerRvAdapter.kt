package com.android.tools.Activities.Banners

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.android.tools.R
import com.bumptech.glide.Glide


class BannerRvAdapter(
    private val context: Context,
    private val mBanner: List<Banner>,
    private val listener: RecyclerTouchListener
) :
    RecyclerView.Adapter<BannerRvAdapter.BannerHolder>() {
    interface RecyclerTouchListener {
        fun onClickBannerItem(titel: String?, position: Int)
    }

    var ImageUrl = "http://pixeldev.in/webservices/e_commerce/admin/"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_banner_item, parent, false)
        return BannerHolder(view)
    }

    override fun onBindViewHolder(holder: BannerHolder, position: Int) {
        Glide.with(context).load(ImageUrl + mBanner[position].img).thumbnail(
            Glide.with(
                context
            ).load(R.drawable.app_bg)
        ).centerCrop().into(holder.imgBanner)
        holder.imgBanner.setOnClickListener { view: View? ->
            listener.onClickBannerItem(
                mBanner[position].id, mBanner[position].positions
            )
        }
    }

    override fun getItemCount(): Int {
        return mBanner.size
    }

    inner class BannerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgBanner: ImageView

        init {
            imgBanner = itemView.findViewById(R.id.img_banner)
        }
    }
}