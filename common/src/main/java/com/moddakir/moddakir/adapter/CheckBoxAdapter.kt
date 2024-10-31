package com.moddakir.moddakir.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.example.moddakirapps.R
import com.moddakir.moddakir.network.model.CheckBoxModel

class CheckBoxAdapter(val checkboxList: ArrayList<CheckBoxModel>, private val context:Context) :
    RecyclerView.Adapter<CheckBoxAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.view_checkbox_item, parent, false)
        val holder = ViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val checkBoxModel = checkboxList[position]

        if (checkBoxModel.isSelected != null) {
            if (checkBoxModel.isSelected!!) {
                holder.checkBox.isChecked = true
            } else {
                holder.checkBox.isChecked = false
            }
        }


        holder.checkBox.text = checkBoxModel.value

        holder.checkBox.setOnCheckedChangeListener { compoundButton: CompoundButton, b: Boolean ->
            checkboxList[position].isSelected=(compoundButton.isChecked)
            holder.checkBox.backgroundTintList = ColorStateList.valueOf(
                context.resources.getColor(R.color.colorPrimaryStudentTeacher)
            )
        }
    }

    override fun getItemCount(): Int {
        return checkboxList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val checkBox: CheckBox = view.findViewById(R.id.checkbox)
    }

}
