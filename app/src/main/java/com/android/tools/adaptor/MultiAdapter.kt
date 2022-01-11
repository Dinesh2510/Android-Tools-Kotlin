package com.android.tools.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.tools.R
import com.android.tools.model.PassionsModel
import java.util.ArrayList

class MultiAdapter(private val context: Context, employees: ArrayList<PassionsModel>) :
    RecyclerView.Adapter<MultiAdapter.MultiViewHolder>() {
    private var PassionsModelArrayList: ArrayList<PassionsModel>
    fun setEmployees(employees: ArrayList<PassionsModel>) {
        PassionsModelArrayList = ArrayList<PassionsModel>()
        PassionsModelArrayList = employees
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MultiViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.item_multi, viewGroup, false)
        return MultiViewHolder(view)
    }

    override fun onBindViewHolder(multiViewHolder: MultiViewHolder, position: Int) {
        multiViewHolder.bind(PassionsModelArrayList[position])
    }

    override fun getItemCount(): Int {
        return PassionsModelArrayList.size
    }

    inner class MultiViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val textView: TextView
        private val imageView: ImageView
        fun bind(employee: PassionsModel) {
            imageView.visibility = if (employee.isChecked) View.VISIBLE else View.GONE
            textView.setText(employee.genre_name)
            itemView.setOnClickListener {
                employee.isChecked=(!employee.isChecked)
                imageView.visibility =
                    if (employee.isChecked) View.VISIBLE else View.GONE
            }
        }

        init {
            textView = itemView.findViewById(R.id.textView)
            imageView = itemView.findViewById(R.id.imageView)
        }
    }

    val all: ArrayList<PassionsModel>
        get() = PassionsModelArrayList
    val getselected: ArrayList<PassionsModel>
        get() {
            val selected: ArrayList<PassionsModel> = ArrayList<PassionsModel>()
            for (i in PassionsModelArrayList.indices) {
                if (PassionsModelArrayList[i].isChecked) {
                    selected.add(PassionsModelArrayList[i])
                }
            }
            return selected
        }

    init {
        PassionsModelArrayList = employees
    }
}