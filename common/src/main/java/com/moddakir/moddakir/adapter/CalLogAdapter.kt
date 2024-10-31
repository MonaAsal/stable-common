package com.moddakir.moddakir.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.moddakirapps.R
import com.moddakir.moddakir.App
import com.moddakir.moddakir.App.Companion.timeZoneOffset
import com.moddakir.moddakir.helper.LocaleHelper
import com.moddakir.moddakir.network.model.Session
import com.moddakir.moddakir.network.model.User
import com.moddakir.moddakir.ui.bases.listeners.SessionListener
import com.moddakir.moddakir.ui.bases.sessions.ReportTeacherDialog
import com.moddakir.moddakir.ui.bases.sessions.SessionDetailsActivity
import com.moddakir.moddakir.ui.widget.ButtonUniqueLight
import com.moddakir.moddakir.ui.widget.TextViewUniqueLight
import com.moddakir.moddakir.utils.AccountPreference
import com.moddakir.moddakir.utils.Utils
import com.potyvideo.library.AndExoPlayerView
import com.potyvideo.library.globalEnums.EnumAspectRatio
import com.potyvideo.library.globalInterfaces.ExoPlayerCallBack
import de.hdodenhof.circleimageview.CircleImageView
import me.zhanghai.android.materialratingbar.MaterialRatingBar

class CalLogAdapter(val exoPlayerCallBack: ExoPlayerCallBack, private val sessionListener: SessionListener) : RecyclerView.Adapter<CalLogAdapter.ViewHolder>()  {
    val utils:Utils= Utils()
    private var source: String? = null
    private var userID=""
    var childId: String = ""
    var childAge: String = ""
    private var callLogList: ArrayList<Session>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.call_log_item, parent, false)
        userID = AccountPreference.getUser()!!.id
        val holder = ViewHolder(view)
        return holder
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val session = callLogList!![position]
        holder.btnShowMore.textSize = 12f
        if (LocaleHelper.getLocale(App.context).toString() == "ar")
            holder.andExoPlayerView.RotateIconInArabic();
        if (userID != null && session.call.studentId != userID) {
            holder.delete.visibility = View.GONE
        } else holder.delete.visibility = View.VISIBLE

        App.utils.loadImage(App.context, session.call.teacherAvatarUrl, holder.civTeacherImage)
        holder.tvName.text = session.call.teacherName
        setSourceUrl(session.call.recordUrl)
        holder.tvDuration.text = session.call.duration
        holder.tvTime.text = App.utils.getDateTimeFormat(
            App.context,
            session.call.startDateTime,
            timeZoneOffset
        )
        if (session.records.size !== 0 && session.records[0].rate != null) {
            holder.mbRatingBar.rating = session.records[0].rate.toFloat()
        } else {
            holder.mbRatingBar.rating = "0".toFloat()
        }


        if (session.call.type == "Voice") {
            holder.andExoPlayerView.setAspectRatio(EnumAspectRatio.ASPECT_MP3)
        } else {
            holder.andExoPlayerView.setAspectRatio(EnumAspectRatio.ASPECT_1_1)
        }
        if (source != null && source!!.trim { it <= ' ' }.isNotEmpty()) {
            holder.andExoPlayerView.setSource(source)
            holder.andExoPlayerView.visibility = View.VISIBLE
        } else {
            holder.andExoPlayerView.visibility = View.GONE
        }
        if (session.call.recordUrl == null || session.call.recordUrl.isEmpty()) {
            holder.ivShare.visibility = View.GONE
        } else {
            holder.ivShare.visibility = View.VISIBLE
        }

        holder.btnShowMore.setOnClickListener { v ->
            SessionDetailsActivity.start(
                holder.itemView.context,
                callLogList!![holder.bindingAdapterPosition],
                childId,
                childAge,
                java.lang.String.valueOf(
                    callLogList!![position].dependentChildRatingTeacher
                ),
                callLogList!![position].dependentChildCommentTeacher
            )
        }

        holder.report.setOnClickListener { view: View? ->
            ReportTeacherDialog.start(
                holder.itemView.context,
                session
            )
        }
        holder.delete.setOnClickListener { v: View? ->
            showAlertDelete(
                holder.bindingAdapterPosition,
                holder.itemView.context
            )
        }
        holder.andExoPlayerView.setExoPlayerCallBack(object : ExoPlayerCallBack {
            override fun onError() {
            }

            override fun onPlay() {
                exoPlayerCallBack.onPlay(holder.andExoPlayerView, position)
            }

            override fun onPlay(AndExoPlayerView: AndExoPlayerView, pos: Int) {
            }
        })


        holder.ivShare.setOnClickListener { v: View? ->
            val link: String
            val session = callLogList!![holder.bindingAdapterPosition]
            link = if (session.call.shareUrl != null && session.call.shareUrl.isNotEmpty()) {
                session.call.shareUrl
            } else {
                session.call.recordUrl
            }
            if (link != null && link.isNotEmpty()) {
                App.utils.share(link, holder.itemView.context)
            }
        }

        val user: User? = AccountPreference.getUser()
        if (user!!.isChildDependent) {
            holder.delete.visibility = View.GONE
        }


    }

    private fun setSourceUrl(recordUrl: String) {
        this.source = recordUrl
    }

    override fun getItemCount(): Int {
        return callLogList!!.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var isPlayed = false
        var source: String? = null
        val mbRatingBar: MaterialRatingBar = view.findViewById(R.id.ratingBar)
        val civTeacherImage: CircleImageView = view.findViewById(R.id.civ_teacher_image)
        val tvName: TextViewUniqueLight = view.findViewById(R.id.tv_name)
        val tvDuration: TextViewUniqueLight = view.findViewById(R.id.tv_duration)
        val tvTime: TextViewUniqueLight = view.findViewById(R.id.tv_time)
        val report: ImageView = view.findViewById(R.id.report)
        val btnShowMore: ButtonUniqueLight = view.findViewById(R.id.btn_show_more)
        val delete: ImageView = view.findViewById(R.id.delete)
        val ivShare: ImageView = view.findViewById(R.id.iv_share)
        val andExoPlayerView: AndExoPlayerView = view.findViewById(R.id.andExoPlayerView)

    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        if (holder.isPlayed) {
            if (holder.source != null && holder.source!!.trim { it <= ' ' }.isNotEmpty()) {
                holder.andExoPlayerView.initializePlayer()
                holder.andExoPlayerView.setSource(holder.source)
            }
        }
        holder.isPlayed = true
    }

    fun getCallLogList(): java.util.ArrayList<Session> {
        return callLogList!!
    }

    fun removeItem(deletedSessionPosition: Int) {
        if (deletedSessionPosition != -1) callLogList!!.removeAt(deletedSessionPosition)
        notifyItemRemoved(deletedSessionPosition)
    }

    fun swapData(data:  ArrayList<Session>?) {
        callLogList = data
        notifyDataSetChanged()
    }

    fun addNewData(data:ArrayList<Session>?) {
        callLogList!!.addAll(data!!)
        notifyDataSetChanged()
    }
    private fun showAlertDelete(position: Int, context: Context) {
        val pDialog = SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
        pDialog.setTitleText(context.resources.getString(R.string.confirmation_message))
        pDialog.setCancelable(true)
        pDialog.setConfirmText(context.resources.getString(R.string.yes))
        pDialog.setCancelText(context.resources.getString(R.string.no))
        pDialog.setCancelClickListener { sweetAlertDialog: SweetAlertDialog? -> pDialog.dismissWithAnimation() }
        pDialog.setConfirmClickListener { sweetAlertDialog: SweetAlertDialog? ->
            pDialog.dismissWithAnimation()
        }
        pDialog.show()
    }
}
