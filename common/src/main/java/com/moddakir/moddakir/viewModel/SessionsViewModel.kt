package com.moddakir.moddakir.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moddakir.moddakir.network.Resource
import com.moddakir.moddakir.network.model.base.BaseViewModel
import com.moddakir.moddakir.network.model.response.BaseResponse
import com.moddakir.moddakir.network.model.response.LookupsResponseModel
import com.moddakir.moddakir.network.model.response.ModdakirResponse
import com.moddakir.moddakir.network.model.response.ResponseModel
import com.moddakir.moddakir.network.model.response.ResponseSessionModel
import com.moddakir.moddakir.network.model.response.TeachersResponse
import com.moddakir.moddakir.useCase.SessionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionsViewModel @Inject constructor(private val sessionsUseCase: SessionsUseCase) : BaseViewModel() {

    var pageNum: Int = 0
    var selectedTeacherId: String = ""
    var selectedTeacherName: String = ""
    var selectedTeacherPosition: Int = 0
    var deletedSessionPosition = -1
    var selectedFromDate: String = ""
    var selectedToDate: String = ""

    var selectedFromDateOnly: String = ""
    var selectedToDateOnly: String = ""
    private val callLogMutableLiveData = MutableLiveData<Resource<ModdakirResponse<ResponseSessionModel>>>()
    val callLogLiveData: MutableLiveData<Resource<ModdakirResponse<ResponseSessionModel>>> = callLogMutableLiveData

    private val teachersMutableLiveData = MutableLiveData<Resource<ModdakirResponse<TeachersResponse>>>()
    val teachersLiveData: MutableLiveData<Resource<ModdakirResponse<TeachersResponse>>> = teachersMutableLiveData

    private val deleteSessionMutableLiveData = MutableLiveData<Resource<ModdakirResponse<BaseResponse>>>()
    val deleteSessionLiveData: MutableLiveData<Resource<ModdakirResponse<BaseResponse>>> = deleteSessionMutableLiveData

    private val lookupsMutableLiveData = MutableLiveData<Resource<ModdakirResponse<LookupsResponseModel>>>()
    val lookupsLiveData: MutableLiveData<Resource<ModdakirResponse<LookupsResponseModel>>> = lookupsMutableLiveData


    private val reportTeacherMutableLiveData = MutableLiveData<Resource<ModdakirResponse<ResponseModel>>>()
    val reportTeacherLiveData: MutableLiveData<Resource<ModdakirResponse<ResponseModel>>> = reportTeacherMutableLiveData


    fun getCallLog(studentId:String,pageIndex:Int) {
        viewModelScope.launch {
            sessionsUseCase.getCallLog(studentId,pageIndex,10,selectedTeacherId,selectedFromDate,selectedToDate)
        }
    }

    fun getTeachers() {
        viewModelScope.launch {
            sessionsUseCase.getTeachers()
        }
    }

    fun deleteSession(sessionId:String) {
        viewModelScope.launch {
            sessionsUseCase.deleteSession(sessionId)
        }
    }

    fun reportTeacher(teacherId:String, comment:String, reasons: ArrayList<String>, sessionId:String) {
        viewModelScope.launch {
            sessionsUseCase.reportTeacher(teacherId,comment,reasons,sessionId)
        }
    }

    fun getLookups() {
        viewModelScope.launch {
            sessionsUseCase.getLookups("ReportTeacher")
        }
    }

    fun rateRequest(teacherId:String,rate:String,comment:String,reasons:String,callId:String,childId:String) {
        viewModelScope.launch {
            sessionsUseCase.rateRequest(teacherId,rate,comment,reasons,callId,childId)
        }
    }




}