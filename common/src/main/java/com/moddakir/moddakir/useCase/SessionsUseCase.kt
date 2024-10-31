package com.moddakir.moddakir.useCase

import com.moddakir.moddakir.network.Resource
import com.moddakir.moddakir.network.model.response.BaseResponse
import com.moddakir.moddakir.network.model.response.EvaluateTeacherResponse
import com.moddakir.moddakir.network.model.response.LookupsResponseModel
import com.moddakir.moddakir.network.model.response.ModdakirResponse
import com.moddakir.moddakir.network.model.response.ResponseModel
import com.moddakir.moddakir.network.model.response.ResponseSessionModel
import com.moddakir.moddakir.network.model.response.TeachersResponse

interface SessionsUseCase {

    suspend fun getCallLog(studentId: String, pageIndex: Int, pageSize: Int,teacherId: String?,fromDate: String?,toDate: String?): Resource<ModdakirResponse<ResponseSessionModel>>

    suspend fun getTeachers(): Resource<ModdakirResponse<TeachersResponse>>

    suspend fun deleteSession(sessionId:String): Resource<ModdakirResponse<BaseResponse>>

    suspend fun reportTeacher(
        teacherId:String,
        comment:String,
        reasons: ArrayList<String>,
        sessionId:String): Resource<ModdakirResponse<ResponseModel>>

    suspend fun getLookups(type: String): Resource<ModdakirResponse<LookupsResponseModel>>

    suspend fun rateRequest(
        teacherId:String,rate:String,comment:String,reasons:String,callId:String,
        childId:String): Resource<ModdakirResponse<EvaluateTeacherResponse>>

}
