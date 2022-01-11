package com.android.tools.Activities.Mvvm_ex

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.tools.Activities.Mvvm_ex.Model.Cat
import com.android.tools.R
import com.bumptech.glide.Glide
import java.util.ArrayList

class Cat_Adapter(private val context: Context, divLikeContent: ArrayList<*>) :
    RecyclerView.Adapter<Cat_Adapter.myholder>() {
    private val employees: ArrayList<Cat> = divLikeContent as ArrayList<Cat>
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): myholder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_category, viewGroup, false)
        return myholder(view)
    }

    override fun onBindViewHolder(holder: myholder, position: Int) {
        holder.textView.setText(employees[position].title)
        Glide.with(holder.itemView.context).load(employees[position].catImg).into(holder.imageview)

    }

    override fun getItemCount(): Int {
        return employees.size
    }

    inner class myholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView
        var imageview: ImageView

        init {
            textView = itemView.findViewById(R.id.txt_name)
            imageview = itemView.findViewById(R.id.imageview)
        }
    }}
