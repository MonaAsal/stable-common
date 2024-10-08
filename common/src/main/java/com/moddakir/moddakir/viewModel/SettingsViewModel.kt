package com.moddakir.moddakir.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moddakir.moddakir.network.Resource
import com.moddakir.moddakir.network.model.AboutModel
import com.moddakir.moddakir.network.model.base.BaseViewModel
import com.moddakir.moddakir.network.model.response.ModdakirResponse
import com.moddakir.moddakir.network.model.response.ResponseModel
import com.moddakir.moddakir.useCase.SettingsUseCase
import com.moddakir.moddakir.useCase.StaticPagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsUseCase: SettingsUseCase
) : BaseViewModel() {
    private val callSettingsMutableLiveData = MutableLiveData<Resource<ModdakirResponse<ResponseModel>>>()
    val callSettingsLiveData: MutableLiveData<Resource<ModdakirResponse<ResponseModel>>> = callSettingsMutableLiveData

    fun changeSettings(enableVoiceRecording:String,enableVideoRecording:String) {
        viewModelScope.launch {
            settingsUseCase.changeSettings(enableVoiceRecording,enableVideoRecording)
        }
    }


}