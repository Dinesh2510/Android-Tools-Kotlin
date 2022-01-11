package com.android.tools.Activities.Mvvm_ex

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.android.tools.Activities.Mvvm_ex.Model.Bannermv
import com.android.tools.R
import com.bumptech.glide.Glide


class BannerAdapter_mvvm(
    private val context: Context,
    mBanner: List<Bannermv>,listener: RecyclerTouchListener
) : RecyclerView.Adapter<BannerAdapter_mvvm.BannerHolder>() {

    private val mBanner: List<Bannermv> = mBanner
    private val listener: RecyclerTouchListener = listener

// listener: RecyclerTouchListener

    interface RecyclerTouchListener {
        fun onClickBannermv(item: Bannermv?, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.adapter_banner_item, parent, false)
        return BannerHolder(view)
    }

    override fun onBindViewHolder(holder: BannerHolder, position: Int) {
        holder.imgBanner?.let {
            Glide.with(context).load(mBanner[position].img)
                .thumbnail(
                    Glide.with(
                        context
                    ).load(R.drawable.ic_placeholder)
                ).centerCrop().into(it)
        }
        holder.imgBanner!!.setOnClickListener { v: View? ->
            listener.onClickBannermv(mBanner[position], position)

        }
    }

    override fun getItemCount(): Int {
        return mBanner.size
    }

    inner class BannerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgBanner: ImageView? = null

        init {
            imgBanner = itemView.findViewById(R.id.img_banner)

        }
    }

}