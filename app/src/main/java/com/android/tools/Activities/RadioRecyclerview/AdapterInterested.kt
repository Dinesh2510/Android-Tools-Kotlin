package com.android.tools.Activities.RadioRecyclerview

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.android.tools.R
import com.android.tools.model.PassionsModel_Parse
import java.util.ArrayList

class AdapterInterested(formListModelArrayList: ArrayList<PassionsModel_Parse>, context: Context) :
    RecyclerView.Adapter<AdapterInterested.MyViewHolder>() {
    var formListModelArrayList: ArrayList<PassionsModel_Parse>
    var context: Context
    private var lastSelectedPosition = -1
    var row_index = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_form_list, parent, false) as View
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val designModel: PassionsModel_Parse = formListModelArrayList[position]
        holder.tvFormName.setText(designModel.genreName)
        holder.tvFormName.isChecked = lastSelectedPosition == position
        /*holder.tvFormName.setOnClickListener {
            lastSelectedPosition = holder.adapterPosition
            notifyDataSetChanged()
            (context as RadioActivity).onClickCalled_status(designModel, position, 1)


        }*/

        //code for select and deselect Radio button
        holder.tvFormName.setOnClickListener { view ->
            if (formListModelArrayList.get(position).genreId.equals(row_index.toString(), ignoreCase = true)) {
                holder.tvFormName.setChecked(false)
                row_index = -1
                (context as RadioActivity).onClickCalled_status(designModel, position, row_index)

            } else {
                holder.tvFormName.setChecked(true)
                row_index = formListModelArrayList.get(position).genreId.toInt()
                notifyDataSetChanged()
                (context as RadioActivity).onClickCalled_status(designModel, position, row_index)

            }
        }

        if (row_index == formListModelArrayList.get(position).genreId.toInt()) {
            holder.tvFormName.setChecked(true)
        } else {
            holder.tvFormName.setChecked(false)
        }

    }

    override fun getItemCount(): Int {
        return formListModelArrayList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvFormName: RadioButton

        init {
            tvFormName = itemView.findViewById(R.id.tvFormName)
        }
    }

    init {
        this.formListModelArrayList = formListModelArrayList
        this.context = context
    }
}