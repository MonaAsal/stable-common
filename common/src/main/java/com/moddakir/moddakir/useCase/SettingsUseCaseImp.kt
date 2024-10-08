package com.moddakir.moddakir.useCase

import com.moddakir.moddakir.network.Resource
import com.moddakir.moddakir.network.model.response.ModdakirResponse
import com.moddakir.moddakir.network.model.response.ResponseModel
import com.moddakir.moddakir.network.remote.RemoteRepository
import javax.inject.Inject

class SettingsUseCaseImp @Inject constructor(
    private val repository: RemoteRepository
) : SettingsUseCase {
    override suspend fun changeSettings(
        enableVoiceRecording: String,
        enableVideoRecording: String
    ): Resource<ModdakirResponse<ResponseModel>> {
        return repository.contactUsForm(enableVoiceRecording,enableVideoRecording)

    }


}