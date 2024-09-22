package com.moddakir.moddakir.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moddakirapps.R
import com.moddakir.moddakir.model.SavedFingerAccount
import com.moddakir.moddakir.ui.bases.listeners.RecyclerItemListener
import com.moddakir.moddakir.ui.widget.TextViewCalibriBold
class AccountsAdapter( private val listener: RecyclerItemListener<SavedFingerAccount>) :
    RecyclerView.Adapter<AccountsAdapter.ViewHolder>() {
    private var accounts: ArrayList<SavedFingerAccount?>? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.accounts_row, parent, false)
        val holder = ViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvName.setText(accounts!![position]?.userName)
        holder.tvName.setOnClickListener { v ->
            listener.onItemSelected(accounts!![position]!!)
        }
    }

    override fun getItemCount(): Int {
        return accounts!!.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextViewCalibriBold = view.findViewById(R.id.accountname)
    }

}
