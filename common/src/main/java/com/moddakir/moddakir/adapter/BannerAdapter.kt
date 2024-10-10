package com.moddakir.moddakir.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.moddakirapps.R
import com.moddakir.moddakir.network.model.BannerModel
import com.moddakir.moddakir.ui.bases.listeners.BannerClickListener
import com.moddakir.moddakir.utils.Utils

class BannerAdapter(
    private val bannerList: ArrayList<BannerModel>,
    private val context: Context,
    private val bannerClickListener: BannerClickListener
) :
    RecyclerView.Adapter<BannerAdapter.ViewHolder>() {
    val utils: Utils = Utils()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.banner_item, parent, false)
        val holder = ViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        utils.loadImage(context, bannerList[position].image, holder.bannerImage);
        if (bannerList[position].type == "video") {
            holder.playIcon.visibility = View.VISIBLE
        } else {
            holder.playIcon.visibility = View.GONE
        }
        holder.bannerImage.setOnClickListener { v: View? ->
            bannerClickListener.onBannerClicked(position,bannerList[holder.bindingAdapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return bannerList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bannerImage: ImageView = view.findViewById(R.id.banner_image)
        val playIcon: ImageView = view.findViewById(R.id.play_icon)
    }

}
