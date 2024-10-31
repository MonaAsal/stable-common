package com.moddakir.moddakir.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.moddakirapps.R
import com.moddakir.moddakir.network.model.Item
import com.moddakir.moddakir.ui.bases.listeners.OnHistoryClickListener
import com.moddakir.moddakir.ui.widget.TextViewCalibriBold
import com.moddakir.moddakir.utils.Utils
import timber.log.Timber

class TicketsHistoryAdapter(val context: Context, private val listener: OnHistoryClickListener, private val tickets: ArrayList<Item>?) :
    RecyclerView.Adapter<TicketsHistoryAdapter.ViewHolder>()  {
  val  utils:Utils= Utils()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.history_row, parent, false)
        val holder = ViewHolder(view)
        return holder
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = tickets!![position].title
        holder.date.text = utils.getDateTimeFormat(
            this.context,
            tickets[position].date!!,
            0
        )
        holder.itemView.setOnClickListener { v: View? ->
            Timber.d("position = " + holder.bindingAdapterPosition)
            listener.onTicketClicked(tickets[holder.bindingAdapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return tickets!!.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val title: TextViewCalibriBold = view.findViewById(R.id.ticket_title)
        val date: TextView = view.findViewById(R.id.ticket_date)
    }

}
