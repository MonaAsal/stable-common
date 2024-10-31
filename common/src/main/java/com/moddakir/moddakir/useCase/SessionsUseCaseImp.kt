package com.moddakir.moddakir.useCase

import com.moddakir.moddakir.network.Resource
import com.moddakir.moddakir.network.model.response.BaseResponse
import com.moddakir.moddakir.network.model.response.EvaluateTeacherResponse
import com.moddakir.moddakir.network.model.response.LookupsResponseModel
import com.moddakir.moddakir.network.model.response.ModdakirResponse
import com.moddakir.moddakir.network.model.response.ResponseModel
import com.moddakir.moddakir.network.model.response.ResponseSessionModel
import com.moddakir.moddakir.network.model.response.TeachersResponse
import com.moddakir.moddakir.network.remote.RemoteRepository
import javax.inject.Inject

class SessionsUseCaseImp @Inject constructor(private val repository: RemoteRepository) : SessionsUseCase {

    override suspend fun getCallLog(
        studentId: String, pageIndex: Int, pageSize: Int,teacherId: String?,fromDate: String?,toDate: String?
    ): Resource<ModdakirResponse<ResponseSessionModel>> {
        return repository.getCallLog(studentId,pageIndex,pageSize,teacherId,fromDate,toDate)
    }

    override suspend fun getTeachers(): Resource<ModdakirResponse<TeachersResponse>> {
        return repository.getTeachers()
    }

    override suspend fun deleteSession(sessionId: String): Resource<ModdakirResponse<BaseResponse>> {
        return repository.deleteSession(sessionId)
    }
    override suspend fun reportTeacher(
        teacherId:String,
        comment:String,
        reasons: ArrayList<String>,
        sessionId:String): Resource<ModdakirResponse<ResponseModel>> {
        return repository.reportTeacher(teacherId,comment,reasons,sessionId)
    }
    override suspend fun getLookups(type:String): Resource<ModdakirResponse<LookupsResponseModel>> {
        return repository.getLookups(type)
    }

    override suspend fun rateRequest(
        teacherId: String,
        rate: String,
        comment: String,
        reasons: String,
        callId: String,
        childId: String
    ): Resource<ModdakirResponse<EvaluateTeacherResponse>> {
        return repository.rateRequest(teacherId,rate,comment,reasons,callId,childId)

    }


}