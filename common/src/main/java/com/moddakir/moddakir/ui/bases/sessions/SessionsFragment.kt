package com.moddakir.moddakir.ui.bases.sessions

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moddakirapps.R
import com.example.moddakirapps.databinding.FragmentSessionsBinding
import com.moddakir.moddakir.App
import com.moddakir.moddakir.adapter.CalLogAdapter
import com.moddakir.moddakir.adapter.TeachersSpinnerAdapter
import com.moddakir.moddakir.helper.SharedPrefHelper.Companion.setIntoSharedPref
import com.moddakir.moddakir.network.Resource
import com.moddakir.moddakir.network.model.User
import com.moddakir.moddakir.network.model.base.BaseFragment
import com.moddakir.moddakir.network.model.response.ModdakirResponse
import com.moddakir.moddakir.network.model.response.ResponseSessionModel
import com.moddakir.moddakir.network.model.response.TeachersResponse
import com.moddakir.moddakir.ui.bases.listeners.SessionListener
import com.moddakir.moddakir.ui.widget.ButtonCalibriBold
import com.moddakir.moddakir.ui.widget.TextViewUniqueLight
import com.moddakir.moddakir.utils.AccountPreference
import com.moddakir.moddakir.utils.Utils
import com.moddakir.moddakir.utils.observe
import com.moddakir.moddakir.viewModel.SessionsViewModel
import com.potyvideo.library.AndExoPlayerView
import com.potyvideo.library.globalInterfaces.ExoPlayerCallBack
import java.util.Calendar
import java.util.Date

class SessionsFragment : BaseFragment(), DatePickerDialog.OnDateSetListener, SessionListener {
    private var currentAndExoPlayerView: AndExoPlayerView? = null
    var currentAndExoPlayerViewPos: Int = -1
    private lateinit var dialog :Dialog
    private lateinit var teachers: Spinner
    override val layoutId: Int get() = R.layout.fragment_sessions
    private lateinit var binding: FragmentSessionsBinding
    private val viewModel: SessionsViewModel by viewModels()
    private var fromLayout: Boolean = true
    private var _day = 0
    private var _month = 0
    private var _birthYear = 0
    private lateinit var callLogAdapter:CalLogAdapter
    var user: User? = null
    var utils= Utils()
    private lateinit var fromTv: TextViewUniqueLight
    private lateinit var toTv:TextViewUniqueLight
    override fun observeViewModel() {
        observe(viewModel.callLogLiveData, ::handleCallLogResponse)
        observe(viewModel.teachersLiveData, ::handleTeachersResponse)
    }

    private fun handleTeachersResponse(resource: Resource<ModdakirResponse<TeachersResponse>>?) {
        val mySpinnerAdapter = TeachersSpinnerAdapter(App.context, R.layout.spinner_item, resource!!.data!!.data!!.teachers)

        teachers.adapter = mySpinnerAdapter
        teachers.setSelection(0)
        teachers.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View,
                position: Int,
                l: Long
            ) {
                if (position != 0) {
                    viewModel.selectedTeacherName= resource.data!!.data!!.teachers[position].fullName
                    viewModel.selectedTeacherId= resource.data.data!!.teachers[position].id
                    viewModel.selectedTeacherPosition= position
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }
        }
    }

    private fun handleCallLogResponse(resource: Resource<ModdakirResponse<ResponseSessionModel>>?) {

        when (resource) {
            is Resource.Loading -> {
                binding.btnFilter.isEnabled = false
            }

            is Resource.Success -> resource.data?.let {
                if (resource.data.data!!.items!= null) {
                    if (viewModel.pageNum == 0) {
                        callLogAdapter.swapData(resource.data.data.items)
                    } else {
                        callLogAdapter.addNewData(resource.data.data.items)
                    }
                    binding.rvCallLog.visibility = View.VISIBLE
                    binding.emptyVew.visibility = View.GONE
                }
                if (viewModel.pageNum == 0) {
                    if (resource.data.data.items== null || resource.data.data.items!!.isEmpty()
                    ) {
                        binding.rvCallLog.visibility = View.GONE
                        binding.emptyVew.visibility = View.VISIBLE
                    }
                }
            }

            is Resource.NetworkError -> {
                resource.errorCode?.let {
                }
            }

            is Resource.DataError -> {
                Toast.makeText(
                    App.context,
                    resources.getString(R.string.server_error),
                    Toast.LENGTH_LONG
                ).show()
            }
            else -> {}
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSessionsBinding.inflate(layoutInflater)

        binding.btnFilter.setOnClickListener{
            openFilterDialog()
        }
        return binding.getRoot()

    }

    private fun openFilterDialog() {
        dialog = Dialog(requireActivity())
        dialog.setContentView(R.layout.filter_session_dilog)
        val fromLayoutTxt = dialog.findViewById<RelativeLayout>(R.id.from_Layout)
        val toLayout = dialog.findViewById<RelativeLayout>(R.id.tolayout)
        val btnShowResults: ButtonCalibriBold = dialog.findViewById(R.id.btn_show_results)
        val tvClearAll: TextViewUniqueLight = dialog.findViewById(R.id.tv_clear_all)
        teachers = dialog.findViewById(R.id.tv_teachers)
        val ivClose = dialog.findViewById<ImageView>(R.id.iv_close)
         fromTv = dialog.findViewById(R.id.from)
         toTv = dialog.findViewById(R.id.to)
        //set data if shared not null
        if (viewModel.selectedTeacherName.isNotEmpty() || viewModel.selectedFromDate != "") {
            teachers.setSelection(viewModel.selectedTeacherPosition)
        }
        if (viewModel.selectedFromDate.isNotEmpty() || viewModel.selectedFromDate != "") {
            fromTv.text = viewModel.selectedFromDateOnly
        }
        if (viewModel.selectedToDate.isNotEmpty() || viewModel.selectedToDate != "") {
            toTv.text = viewModel.selectedToDateOnly
        }

        ivClose.setOnClickListener { dialog.dismiss() }
        fromLayoutTxt.setOnClickListener {
            fromLayout = true
            showCalendar()
        }
        toLayout.setOnClickListener {
            if (viewModel.selectedFromDate.isNotEmpty()) {
                fromLayout = false
                showCalendar()
            } else {
                showMessage(requireActivity().getString(R.string.choose_from_time))
            }
        }

        tvClearAll.setOnClickListener {
            dialog.dismiss()
            viewModel.pageNum = 0
            clearAllFilterData()
            teachers.setSelection(0)
            fromTv.text = ""
            toTv.text = ""
            setupCallLogRV()
            viewModel.getCallLog(user!!.id,viewModel.pageNum)
        }
        btnShowResults.setOnClickListener { view ->
            dialog.dismiss()
            viewModel.pageNum = 0
            setupCallLogRV()
            viewModel.getCallLog(user!!.id,viewModel.pageNum)
            saveFilterAtShared()
            redFilterButton()
        }

        teachers.setOnClickListener {
            viewModel.getTeachers()
        }

        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.setCancelable(false)
        dialog.show()
    }
    private fun showCalendar() {
        val myCalendar = Calendar.getInstance()
        val dialog = DatePickerDialog(
            requireActivity(), R.style.datepicker, this,
            myCalendar[Calendar.YEAR],
            myCalendar[Calendar.MONTH],
            myCalendar[Calendar.DAY_OF_MONTH]
        )
        dialog.datePicker.maxDate = Date().time
        if (!fromLayout) dialog.datePicker.minDate =
            utils.getDateFromUTC(viewModel.selectedFromDate).time

        dialog.show()
    }


    override fun onDateSet(datePicker: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        _birthYear = year
        _month = monthOfYear
        _day = dayOfMonth
        updateDesignWithDate()
    }
    private fun updateDesignWithDate() {
        val selectedDate: String = utils.getFormattedDateUTC("", _day, _birthYear, _month + 1)!!
        if (fromLayout) {
            fromTv.text = StringBuilder()
                .append(_day).append("/").append(_month + 1).append("/").append(_birthYear)
                .append(" ")
            viewModel.selectedFromDate = selectedDate
            viewModel.selectedFromDateOnly = java.lang.String.valueOf(fromTv.getText())
        } else {
            toTv.text = StringBuilder()
                .append(_day).append("/").append(_month + 1).append("/").append(_birthYear)
                .append(" ")
            viewModel.selectedToDate = selectedDate
            viewModel.selectedToDateOnly = java.lang.String.valueOf(toTv.getText())
        }
    }

    private fun setupCallLogRV() {
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.rvCallLog.setLayoutManager(layoutManager)
         callLogAdapter = CalLogAdapter(object : ExoPlayerCallBack {
            override fun onError() {}
            override fun onPlay() {}
            override fun onPlay(andExoPlayerView: AndExoPlayerView, pos: Int) {
                if (this@SessionsFragment.currentAndExoPlayerView != null && pos != currentAndExoPlayerViewPos) {
                    this@SessionsFragment.currentAndExoPlayerView!!.pausePlayer()
                }
                this@SessionsFragment.currentAndExoPlayerView = andExoPlayerView
                this@SessionsFragment.currentAndExoPlayerViewPos = pos
            }
        }, this)
        binding.rvCallLog.setAdapter(callLogAdapter)
        binding.rvCallLog.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)){
                    viewModel.pageNum++
                    if (user == null) user = AccountPreference.getUser()
                    viewModel.getCallLog(user!!.id,viewModel.pageNum)
                }
            }

        })
    }
    private fun clearAllFilterData() {
        clearFilter()
        viewModel.selectedTeacherId = ""
        viewModel.selectedTeacherName = ""
        viewModel.selectedFromDate = ""
        viewModel.selectedToDate = ""
        viewModel.selectedFromDateOnly = ""
        viewModel.selectedToDateOnly = ""
        greenFilterButton()
    }
    private fun clearFilter() {
        setIntoSharedPref(App.context, "FilterData", "")
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun redFilterButton() {
        binding.btnFilter.setTextColor(requireContext().resources.getColor(R.color.darkred))
        binding.btnFilter.setCompoundDrawablesWithIntrinsicBounds(
            requireContext().resources.getDrawable(R.drawable.ic_filter_new_red),
            null,
            null,
            null
        )
        binding.btnFilter.background = requireContext().resources.getDrawable(R.drawable.filter_background_red)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun greenFilterButton() {
        binding.btnFilter.setTextColor(requireContext().resources.getColor(R.color.colorPrimaryStudentTeacher))
        binding.btnFilter.setCompoundDrawablesWithIntrinsicBounds(
            requireContext().resources.getDrawable(R.drawable.ic_filter_new),
            null,
            null,
            null
        )
        binding.btnFilter.background = requireContext().resources.getDrawable(R.drawable.filter_background)
    }
    private fun saveFilterAtShared() {
        setIntoSharedPref(App.context, "FilterData", "")
        setIntoSharedPref(App.context, "selectedTeacherId", viewModel.selectedTeacherId)
        setIntoSharedPref(App.context, "selectedTeacherName", viewModel.selectedTeacherName)
        setIntoSharedPref(App.context, "selectedFromDate", viewModel.selectedFromDate)
        setIntoSharedPref(App.context, "selectedToDate", viewModel.selectedToDate)
        setIntoSharedPref(App.context, "selectedFromDateOnly",  viewModel.selectedFromDateOnly)
        setIntoSharedPref(App.context, "selectedToDateOnly", viewModel.selectedToDateOnly)

    }

    override fun onDeleteSessionClicked(position: Int?) {
        viewModel.deletedSessionPosition=(position!!)
        viewModel.deleteSession(callLogAdapter.getCallLogList()[position].id)
    }

}