package com.android.tools.Activities.Mvvm_ex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.tools.Activities.Mvvm_ex.Model.FStock
import com.android.tools.R
import com.bumptech.glide.Glide

class MainAdapter : RecyclerView.Adapter<MainViewHolder>() {

    var moviesList = mutableListOf<FStock>()

    fun setFStockList(movies: List<FStock>) {
        moviesList = movies.toMutableList()
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MainViewHolder {
        val view: View =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.adapter_product, viewGroup, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val movie = moviesList[position]
        holder.textView.text = movie.productTitle
       Glide.with(holder.itemView.context).load(movie.productImg).into(holder.imageview)

    }

    override fun getItemCount(): Int {
        return moviesList.size
    }
}

 class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var textView: TextView
    var imageview: ImageView

    init {
        textView = itemView.findViewById(R.id.txt_name)
        imageview = itemView.findViewById(R.id.imageview)
    }
}
