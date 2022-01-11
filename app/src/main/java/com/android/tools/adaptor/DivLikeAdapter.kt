package com.android.tools.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.tools.R
import com.android.tools.model.PassionsModel
import java.util.ArrayList

class DivLikeAdapter(private val context: Context, divLikeContent: ArrayList<*>) :
    RecyclerView.Adapter<DivLikeAdapter.myholder>() {
    private val employees: ArrayList<PassionsModel> = divLikeContent as ArrayList<PassionsModel>
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): myholder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_text, viewGroup, false)
        return myholder(view)
    }

    override fun onBindViewHolder(holder: myholder, position: Int) {
        holder.textView.setText(employees[position].genre_name)
    }

    override fun getItemCount(): Int {
        return employees.size
    }

    inner class myholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView

        init {
            textView = itemView.findViewById(R.id.text)
        }
    }

}