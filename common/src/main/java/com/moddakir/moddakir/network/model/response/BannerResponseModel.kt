package com.moddakir.moddakir.network.model.response

import android.os.Parcelable
import com.moddakir.moddakir.network.model.BannerModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BannerResponseModel(
    var data: ArrayList<BannerModel>? = null

) : Parcelable