package com.moddakir.moddakir.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.example.moddakirapps.R
import com.moddakir.moddakir.network.model.Student

class DependentManagersAdapter(
    private val normalPathModels: List<com.moddakir.moddakir.network.model.Student>,
    val planClickListener: () -> Unit,
    private var selectedPosition: Int
) :
    RecyclerView.Adapter<DependentManagersAdapter.ViewHolder>() {


    interface PlanClickListener {
        fun onPlanClicked(position: Int)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.dependent_managers_row, parent, false)
        val holder = ViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val item = normalPathModels!![position]
        holder.select_rb.text = item.fullName
        holder.select_rb.isChecked = selectedPosition == position

        holder.select_rb.setOnClickListener { view: View? ->
            if (position != -1) {
                notifyItemChanged(selectedPosition)
                selectedPosition = position
                notifyItemChanged(selectedPosition)
                //planClickListener?.onPlanClicked(position)
            }
        }

    }

    override fun getItemCount(): Int {
        return normalPathModels!!.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val select_rb: RadioButton = view.findViewById(R.id.select_rb)
    }

}