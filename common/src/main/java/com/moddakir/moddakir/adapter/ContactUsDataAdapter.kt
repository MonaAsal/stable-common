package com.moddakir.moddakir.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.moddakirapps.R
import com.moddakir.moddakir.App
import com.moddakir.moddakir.network.model.SocialMedia

class ContactUsDataAdapter( private val items: ArrayList<SocialMedia>?) :
    RecyclerView.Adapter<ContactUsDataAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.contact_us_item, parent, false)
        val holder = ViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.socialMediaItem.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(items!![position].url))
            App.context.startActivity(browserIntent)
        }
        App.utils.loadImage(App.context,items!![position].image,holder.socialMediaItem)
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val socialMediaItem: ImageView = view.findViewById(R.id.ivSocialMedia)
    }

}
