package com.moddakir.moddakir.ui.bases.listeners
import com.moddakir.moddakir.network.model.Item

fun interface OnHistoryClickListener {
    fun onTicketClicked(item: Item?)
}
