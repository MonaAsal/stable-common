package com.moddakir.moddakir.ui.bases.sessions

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moddakirapps.R
import com.example.moddakirapps.databinding.ActivityReportTeacherDialogBinding
import com.moddakir.moddakir.adapter.CheckBoxAdapter
import com.moddakir.moddakir.network.Resource
import com.moddakir.moddakir.network.model.CheckBoxModel
import com.moddakir.moddakir.network.model.Session
import com.moddakir.moddakir.network.model.base.BaseActivity
import com.moddakir.moddakir.network.model.response.LookupsResponseModel
import com.moddakir.moddakir.network.model.response.ModdakirResponse
import com.moddakir.moddakir.network.model.response.ResponseModel
import com.moddakir.moddakir.utils.observe
import com.moddakir.moddakir.viewModel.SessionsViewModel

class ReportTeacherDialog : BaseActivity() {
    override var layoutId: Int = R.layout.activity_report_teacher_dialog
    private var callLog: Session? = null
    private var likesAdapter: CheckBoxAdapter? = null
    private val viewModel: SessionsViewModel by viewModels()
    private lateinit var binding: ActivityReportTeacherDialogBinding
    private val selectedLikesList = ArrayList<String>()
    private val likesList: java.util.ArrayList<CheckBoxModel> = java.util.ArrayList<CheckBoxModel>()
    override fun initializeViewModel() {
        viewModel.getLookups()
    }

    override fun observeViewModel() {
        observe(viewModel.lookupsLiveData, ::handleLookupsResponse)
        observe(viewModel.reportTeacherLiveData, ::handleReportTeacherResponse)
    }

    private fun handleReportTeacherResponse(resource: Resource<ModdakirResponse<ResponseModel>>?) {
        when (resource) {
            is Resource.Loading -> {
                binding.btnSend.isEnabled = false
            }

            is Resource.Success -> resource.data?.let {
                if (resource.data.data!!.statusCode== 401) {
                    Toast.makeText(
                        this@ReportTeacherDialog,
                        resource.data.data.message,
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        this@ReportTeacherDialog,
                        resource.data.data.message,
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                }
            }

            is Resource.NetworkError -> {
                resource.errorCode?.let {
                }
            }

            is Resource.DataError -> {
                Toast.makeText(
                    this@ReportTeacherDialog,
                    resources.getString(R.string.server_error),
                    Toast.LENGTH_LONG
                ).show()
            }
            else -> {}
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun handleLookupsResponse(resource: Resource<ModdakirResponse<LookupsResponseModel>>?) {
        when (resource) {
            is Resource.Loading -> {
                binding.btnSend.isEnabled = false
            }

            is Resource.Success -> resource.data?.let {
                    for (likeModel in resource.data.data!!.items) {
                    likeModel.isSelected=(false)
                    likesList.add(likeModel)
                }
                    likesAdapter!!.notifyDataSetChanged()
            }

            is Resource.NetworkError -> {
                resource.errorCode?.let {
                }
            }

            is Resource.DataError -> {
                Toast.makeText(
                    this@ReportTeacherDialog,
                    resources.getString(R.string.server_error),
                    Toast.LENGTH_LONG
                ).show()
            }
            else -> {}
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityReportTeacherDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTheme(R.style.AppTheme_Dialog)
        setFinishOnTouchOutside(true)
        callLog = intent.getSerializableExtra("CALL_LOG") as Session?

        binding.ivClose.setOnClickListener {
            finish()
        }
        binding.btnSend.setOnClickListener {
            fillLikesSelectedList()
            if (binding.othercheckbox.isChecked && binding.etComment.getText().toString().trim().isEmpty()) {
                Toast.makeText(
                    this@ReportTeacherDialog,
                    resources.getString(R.string.please_add_comment),
                    Toast.LENGTH_LONG
                ).show()
                binding.etComment.error = resources.getString(R.string.please_add_comment)
                return@setOnClickListener
            }
            if (!binding.othercheckbox.isChecked && selectedLikesList.size == 0) {
                Toast.makeText(
                    this@ReportTeacherDialog,
                    resources.getString(R.string.add_reason),
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            viewModel.reportTeacher(callLog!!.call.teacherId,binding.etComment.getText().toString(),selectedLikesList,callLog!!.id)

        }

        binding.othercheckbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                binding.constraintLayout2.visibility = View.VISIBLE
                binding.etComment.setText("")
            } else {
                binding.constraintLayout2.visibility = View.GONE
            }
        })

        setupLikesRV()

    }

    companion object {
        fun start(context: Context?, session: Session) {
            val starter = Intent(context, ReportTeacherDialog::class.java)
            starter.putExtra("CALL_LOG", session)
            context!!.startActivity(starter)
        }
    }

    private fun fillLikesSelectedList() {
        selectedLikesList.clear()
        for (likeModel in likesList) {
            if (likeModel.isSelected == true) {
                selectedLikesList.add(likeModel.id)
            }
        }
    }
    private fun setupLikesRV() {
        val layoutManager: LinearLayoutManager = GridLayoutManager(
            this, 2
        )
        binding.rvLikes.setLayoutManager(layoutManager)
        likesAdapter = CheckBoxAdapter(likesList, applicationContext)
        binding.rvLikes.isNestedScrollingEnabled = false
        binding.rvLikes.setAdapter(likesAdapter)
    }
}
