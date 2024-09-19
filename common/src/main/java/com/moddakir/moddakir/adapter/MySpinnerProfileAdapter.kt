package com.moddakir.moddakir.adapter

import android.content.Context
import android.graphics.Typeface
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.moddakirapps.R
import com.moddakir.moddakir.model.FilterModel

class MySpinnerProfileAdapter(context: Context, resource: Int,items: ArrayList<FilterModel>?) :
    ArrayAdapter<FilterModel>(context, resource, items!!) {
    var font: Typeface = Typeface.createFromAsset(getContext().assets, "font/Calibri_Regular.ttf")
    var context: Context? = null
    var items: ArrayList<FilterModel>? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent!!) as TextView
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
        view.setTypeface(font)
        view.setTextColor(context!!.resources.getColor(if (position == 0) R.color.colorGray else R.color.black))
        view.setText(items!![position].name)
        view.setBackgroundColor(context!!.resources.getColor(R.color.transparent))

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent) as TextView
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
        view.typeface = font
        view.setTextColor(context!!.resources.getColor(R.color.colorPrimaryRattel))
        view.setText(items!![position].name)
        view.setBackgroundColor(context!!.resources.getColor(R.color.transparent))
        return view
    } // Affects opened state of the spinner
}