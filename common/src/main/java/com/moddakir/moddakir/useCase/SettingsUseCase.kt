package com.moddakir.moddakir.useCase

import com.moddakir.moddakir.network.Resource
import com.moddakir.moddakir.network.model.AboutModel
import com.moddakir.moddakir.network.model.response.ModdakirResponse
import com.moddakir.moddakir.network.model.response.ResponseModel


interface SettingsUseCase {

    suspend fun changeSettings(enableVoiceRecording:String,enableVideoRecording:String): Resource<ModdakirResponse<ResponseModel>>


}
