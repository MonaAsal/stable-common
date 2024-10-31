package com.moddakir.moddakir.network.model.response

import android.os.Parcelable
import com.moddakir.moddakir.network.model.CheckBoxModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LookupsResponseModel(
    var message: String,
    val action: String,
    val statusCode: Int,
    var totalTime: String,
    var items: ArrayList<CheckBoxModel>,
) : Parcelable