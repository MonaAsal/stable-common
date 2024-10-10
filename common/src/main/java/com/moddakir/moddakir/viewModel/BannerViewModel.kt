package com.moddakir.moddakir.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moddakir.moddakir.network.Resource
import com.moddakir.moddakir.network.model.AboutModel
import com.moddakir.moddakir.network.model.base.BaseViewModel
import com.moddakir.moddakir.network.model.response.BannerResponseModel
import com.moddakir.moddakir.network.model.response.ModdakirResponse
import com.moddakir.moddakir.network.model.response.ResponseModel
import com.moddakir.moddakir.network.model.response.TicketsResponse
import com.moddakir.moddakir.useCase.BannerUseCase
import com.moddakir.moddakir.useCase.StaticPagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BannerViewModel @Inject constructor(
    private val bannerUseCase: BannerUseCase
) : BaseViewModel() {
    private val bannerMutableLiveData = MutableLiveData<Resource<ModdakirResponse<BannerResponseModel>>>()
    val bannerLiveData: MutableLiveData<Resource<ModdakirResponse<BannerResponseModel>>> = bannerMutableLiveData

    fun getBanners() {
        viewModelScope.launch {
            bannerUseCase.getBanners()
        }
    }

}