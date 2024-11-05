package com.moddakir.moddakir.ui.bases.sessions

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.RatingBar.OnRatingBarChangeListener
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.moddakirapps.R
import com.example.moddakirapps.databinding.ActivitySessionDetailsBinding
import com.moddakir.moddakir.App
import com.moddakir.moddakir.adapter.SessionDetailsAdapter
import com.moddakir.moddakir.helper.LocaleHelper
import com.moddakir.moddakir.network.model.Session
import com.moddakir.moddakir.network.model.SessionRecord
import com.moddakir.moddakir.network.model.User
import com.moddakir.moddakir.network.model.base.BaseActivity
import com.moddakir.moddakir.utils.AccountPreference
import com.moddakir.moddakir.viewModel.SessionsViewModel


class SessionDetailsActivity :  BaseActivity() {
    override val layoutId: Int get() = R.layout.activity_session_details
    private var childid: String? = ""
    private var childAge: String? = ""
    private lateinit var callLogModel: Session
    private var dependentChildTeacherRate: String? = "0"
    private var dependentChildTeacherComment: String? = ""
    var ratingVal: Float = 0.0f
    private var dependentChildTeacherRatingF: Float? = 0.0f
    override fun initializeViewModel() {
    }

    override fun observeViewModel() {
    }

    private lateinit var binding: ActivitySessionDetailsBinding
    private val viewModel: SessionsViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val user: User = AccountPreference.getUser()!!
        binding = ActivitySessionDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backBtn.setOnClickListener{ finish() }
        binding.ratingBarForTeacher.onRatingBarChangeListener =
            OnRatingBarChangeListener { ratingBar, rating, fromUser ->
                rating.also { ratingVal = it }
            }
        childid = intent.getStringExtra("childId")
        childAge = intent.getStringExtra("childAge")
        dependentChildTeacherRate = intent.getStringExtra("dependentChildTeacherRate")
        dependentChildTeacherComment = intent.getStringExtra("dependentChildTeacherComment")
        dependentChildTeacherRatingF = dependentChildTeacherRate?.toFloat()
        binding.ratingBarForTeacher.rating = dependentChildTeacherRatingF!!
        binding.commentEt.setText(dependentChildTeacherComment)

        val bundle = intent.extras
        if (bundle != null) {
            callLogModel = bundle.getSerializable("calllog") as Session
            if (callLogModel.call.recordUrl == null || callLogModel.call?.recordUrl!!.isEmpty()
            ) {
                binding.ivShare.visibility = View.GONE
            }
            binding.ivShare.setOnClickListener{
                val link: String =
                    if (callLogModel.call.shareUrl != null && callLogModel.call.shareUrl.isNotEmpty()
                    ) {
                        callLogModel.call.shareUrl
                    } else {
                        callLogModel.call.recordUrl
                    }
                if (link != null && !link.isEmpty()) {
                    App.utils.share(link, this@SessionDetailsActivity)
                }
            }

            if (callLogModel.call.studentId != AccountPreference.getUser()!!.id) {
                binding.delete.visibility = View.GONE
            }
            if (callLogModel != null) {
                showParentEvaluateTeacherCard(user, callLogModel)

                App.utils.loadImage(this, callLogModel.call.teacherAvatarUrl, binding.civTeacherImage)
                binding.tvName.text = callLogModel.call.teacherName
                binding.tvDuration.text = callLogModel.call.duration

                binding.tvTime.text = App.utils.getDateTimeFormat(
                    this,
                    callLogModel.call.startDateTime,
                    0
                )
                if (callLogModel.records != null && callLogModel.records.size >= 1) {
                    setupCallLogRV(callLogModel.records)
                    if (callLogModel.records[0].rate != null) binding.ratingBar.rating =
                        callLogModel.records[0].rate.toFloat()
                    else {
                        binding.ratingBar.rating = "0".toFloat()
                    }
                }
                binding.report.setOnClickListener(View.OnClickListener { view: View? ->
                    ReportTeacherDialog.start(
                        this,
                        callLogModel
                    )
                })
                binding.delete.setOnClickListener(View.OnClickListener { v: View? ->
                    showAlertDelete(
                        callLogModel.id
                    )
                })
               /* if (LocaleHelper.getLocale(this).toString() == "ar")
                    binding.audio.andExoPlayerView.RotateIconInArabic()
                if (callLogModel.call.type == "Voice") {
                    binding.audio.andExoPlayerView.setAspectRatio(EnumAspectRatio.ASPECT_MP3)
                }
                if (callLogModel.call.recordUrl != null && callLogModel.call
                        .recordUrl.trim().isNotEmpty()
                ) {
                    binding.audio.andExoPlayerView.initializePlayer()
                    binding.audio.andExoPlayerView.setSource(callLogModel.call.recordUrl)
                    binding.audio.andExoPlayerView.setExoPlayerCallBack(object : ExoPlayerCallBack {
                        override fun onError() {
                        }

                        override fun onPlay() {
                        }

                        override fun onPlay(andExoPlayerView: AndExoPlayerView, postion: Int) {
                        }
                    })
                } else {
                    binding.audio.andExoPlayerView.visibility = View.GONE
                }
                */
            }
        }

    }

    companion object {
        fun start(
            context: Context?,
            session: Session,
            childId: String,
            childAge: String,
            dependentChildTeacherRate: String,
            dependentChildTeacherComment: String?
        ) {
            val intent = Intent(context, SessionDetailsActivity::class.java)
            intent.putExtra("calllog", session)
            intent.putExtra("childId", childId)
            intent.putExtra("childAge", childAge)
            intent.putExtra("dependentChildTeacherRate", dependentChildTeacherRate)
            intent.putExtra("dependentChildTeacherComment", dependentChildTeacherComment)

            context!!.startActivity(intent)

        }
    }

    private fun showParentEvaluateTeacherCard(user: User, callLogModel: Session) {
        if (!user.isChildDependent) {
            binding.parentEvaluateTeacherCard.visibility = View.VISIBLE
            evaluteTeacherButtonClicked(callLogModel)
        } else {
            binding.parentEvaluateTeacherCard.visibility = View.GONE
        }
    }

    private fun evaluteTeacherButtonClicked(callLogModel: Session) {
        binding.ratingBtnForTeacher.setOnClickListener(View.OnClickListener { view: View? ->
            if (ratingVal != 0.0f) {
                callRatingApi(binding.ratingBarForTeacher.rating, callLogModel)
            } else {
                Toast.makeText(
                    this,
                    resources.getString(R.string.evaluaterequired),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
    private fun showAlertDelete(sessionId: String) {
        val pDialog = SweetAlertDialog(this@SessionDetailsActivity, SweetAlertDialog.ERROR_TYPE)
        pDialog.setTitleText(resources.getString(R.string.confirmation_message))
        pDialog.setCancelable(true)
        pDialog.setConfirmText(resources.getString(R.string.yes))
        pDialog.setCancelText(resources.getString(R.string.no))
        pDialog.setCancelClickListener { sweetAlertDialog: SweetAlertDialog? -> pDialog.dismissWithAnimation() }
        pDialog.setConfirmClickListener { sweetAlertDialog: SweetAlertDialog? ->
            pDialog.dismissWithAnimation()

            deleteSession(sessionId)
        }
        pDialog.show()
    }
    private fun deleteSession(sessionId: String) {
        viewModel.deleteSession(sessionId)
    }
    private fun callRatingApi(rating: Float, callLogModel: Session) {
        val ratingVal = Math.round(rating)
        var comment = ""

        if (binding.commentEt.getText() != null) {
            comment = binding.commentEt.getText().toString()
            callLogModel.dependentChildCommentTeacher=(comment)
        }
        callLogModel.dependentChildRatingTeacher=(ratingVal)
        viewModel.rateRequest(callLogModel.call.teacherId,ratingVal.toString(),comment,"", callLogModel.call.callId, childid!!)
    }
    private fun setupCallLogRV(list: ArrayList<SessionRecord>) {
        for (record in list) {
            if (record.comments != null && !record.comments.isEmpty()) {
                if (record.comment != null && record.comment.isNotEmpty()
                ) record.comment=(
                    record.comment + "," + System.lineSeparator() + TextUtils.join(
                        "," + System.lineSeparator(),
                        record.comments
                    )
                )
                else record.comment=(
                    TextUtils.join(
                        "," + System.lineSeparator(),
                        record.comments
                    )
                )
            }
        }
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recordSession.setLayoutManager(layoutManager)
        val sessionDetailsAdapter: SessionDetailsAdapter = SessionDetailsAdapter(this, list)
        binding.recordSession.setAdapter(sessionDetailsAdapter)
    }
}