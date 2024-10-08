package com.moddakir.moddakir.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.moddakirapps.R
import com.moddakir.moddakir.App.Companion.timeZoneOffset
import com.moddakir.moddakir.network.model.Reply
import com.moddakir.moddakir.ui.bases.staticPages.ZoomImageActivity
import com.moddakir.moddakir.ui.widget.TextViewCalibriBold
import com.moddakir.moddakir.utils.Utils
import de.hdodenhof.circleimageview.CircleImageView

class TicketRepliesAdapter(val context: Context, private val replies: List<Reply>?) :
    RecyclerView.Adapter<TicketRepliesAdapter.ViewHolder>() {


    val utils: Utils = Utils()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.ticket_replies_row, parent, false)
        val holder = ViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.user.text = replies!![position].sender!!.fullName
        holder.reply.text = replies[position].content
        holder.date.text = utils.getTimeFormat(replies[position].date!!, timeZoneOffset)
        utils.loadImage(context, replies[position].sender!!.avatarUrl, holder.avatar)
        holder.card_Imageview_videoview.setOnClickListener {
            if (replies[position].file != null) {
                val zoomActivity = ZoomImageActivity()
                zoomActivity.start(context, replies[position].file)
            }
        }


        //image......
        if (replies[position].fileType != null) {
            if (replies[position].fileType.equals("Image")) {
                holder.card_Imageview_videoview.visibility = View.VISIBLE
                holder.video_image.visibility = View.GONE
                holder.card_audioview.visibility = View.GONE
                utils.loadImage(context, replies[position].file, holder.imageView)

                //video......
            } else if (replies[position].fileType.equals("Video")) {
                holder.card_Imageview_videoview.visibility = View.VISIBLE
                holder.video_image.visibility = View.VISIBLE
                holder.card_audioview.visibility = View.GONE

                //audio......
            } else if (replies[position].fileType.equals("Audio")) {
                holder.card_Imageview_videoview.visibility = View.GONE
                holder.video_image.visibility = View.GONE
                holder.card_audioview.visibility = View.VISIBLE
            } else {
                holder.card_Imageview_videoview.visibility = View.GONE
                holder.video_image.visibility = View.GONE
                holder.card_audioview.visibility = View.GONE
            }
        }

    }

    override fun getItemCount(): Int {
        return replies!!.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val user: TextViewCalibriBold = view.findViewById(R.id.username)
        val date: TextViewCalibriBold = view.findViewById(R.id.ticket_date)
        val reply: TextView = view.findViewById(R.id.reply)
        val avatar: CircleImageView = view.findViewById(R.id.avatar)
        val imageView: ImageView = view.findViewById(R.id.replyImageView)
        val video_image: ImageView = view.findViewById(R.id.video_image)
        val card_Imageview_videoview: CardView = view.findViewById(R.id.card_Imageview_videoview)
        val card_audioview: CardView = view.findViewById(R.id.card_audioview)
    }

}
