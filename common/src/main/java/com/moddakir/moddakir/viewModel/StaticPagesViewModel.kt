package com.moddakir.moddakir.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moddakir.moddakir.network.Resource
import com.moddakir.moddakir.network.model.AboutModel
import com.moddakir.moddakir.network.model.base.BaseViewModel
import com.moddakir.moddakir.network.model.response.ModdakirResponse
import com.moddakir.moddakir.network.model.response.ResponseModel
import com.moddakir.moddakir.useCase.StaticPagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StaticPagesViewModel @Inject constructor(
    private val staticPagesUseCase: StaticPagesUseCase
) : BaseViewModel() {
    private val aboutUsMutableLiveData = MutableLiveData<Resource<ModdakirResponse<AboutModel>>>()
    val aboutUsLiveData: MutableLiveData<Resource<ModdakirResponse<AboutModel>>> = aboutUsMutableLiveData

    private val contactUsMutableLiveData = MutableLiveData<Resource<ModdakirResponse<ResponseModel>>>()
    val contactUsLiveData: MutableLiveData<Resource<ModdakirResponse<ResponseModel>>> = contactUsMutableLiveData
    fun getAboutUs() {
        viewModelScope.launch {
            staticPagesUseCase.getAboutUs()
        }
    }
    fun contactUsForm(message:String,title:String) {
        viewModelScope.launch {
            staticPagesUseCase.contactUsForm(message,title)
        }
    }

    fun getListContactUs(page:Int) {
        viewModelScope.launch {
            staticPagesUseCase.getListContactUs(page)
        }
    }




}