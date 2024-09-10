package com.moddakir.moddakir.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moddakirapps.R
import com.moddakir.moddakir.utils.Language
import com.moddakir.moddakir.view.bases.listeners.OptionLanguageClickListener
import com.moddakir.moddakir.view.widget.ButtonCalibriBold

class LangOptionsAdapter(
    listOfLanguages: List<Language>,
    onCallOptionClickListener: OptionLanguageClickListener
) :
    RecyclerView.Adapter<LangOptionsAdapter.ViewHolder>() {
    private val data: List<Language>? = null
    private val onCallOptionClickListener: OptionLanguageClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.option_item_lang, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.optionName.setText(data!![position].code)

        holder.itemView.setOnClickListener { view: View? ->
            onCallOptionClickListener!!.onOptionClicked(
                data!![position]
            )
        }
    }


    override fun getItemCount(): Int {
        return data!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var optionName: ButtonCalibriBold = itemView.findViewById(R.id.optionName)
    }
}