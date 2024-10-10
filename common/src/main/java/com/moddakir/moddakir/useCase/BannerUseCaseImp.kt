package com.moddakir.moddakir.useCase

import com.moddakir.moddakir.network.Resource
import com.moddakir.moddakir.network.model.response.BannerResponseModel
import com.moddakir.moddakir.network.model.response.ModdakirResponse
import com.moddakir.moddakir.network.remote.RemoteRepository
import javax.inject.Inject

class BannerUseCaseImp @Inject constructor(
    private val repository: RemoteRepository
) : BannerUseCase {

    override suspend fun getBanners(): Resource<ModdakirResponse<BannerResponseModel>> {
        return repository.getBanners()
    }


}