package com.moddakir.moddakir.ui.widget

import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.moddakirapps.R

class MySpinnerAdapter: ArrayAdapter<String> {

    var items: ArrayList<String>? = null
    var booleans: ArrayList<Boolean>? = null
   var con: Context

   constructor(
        context: Context?,
        resource: Int,
        items: ArrayList<String>?,
        booleans: ArrayList<Boolean>?
    ):super(context!!, resource, items!!) {

        this.items = items
        this.con = context
        this.booleans = booleans
    }

    override fun isEnabled(position: Int): Boolean {
        return !booleans!![position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent!!) as TextView
        view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14F)
        //        view.setTypeface(font);
        Log.e("MySpinnerAdapter", items!![position])
        view.text = items!![position]
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent) as TextView
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
        //        view.setTypeface(font);
        if (booleans!![position]) view.setBackgroundColor(context!!.resources.getColor(R.color.greyish))
        else view.setBackgroundColor(context!!.resources.getColor(R.color.colorWhite))
        Log.e("MySpinnerAdapter", items!![position])
        view.text = items!![position]
        return view
    } // Affects opened state of the spinner
}