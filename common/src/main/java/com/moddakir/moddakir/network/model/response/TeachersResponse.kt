package com.moddakir.moddakir.network.model.response

import android.os.Parcelable
import com.moddakir.moddakir.network.model.User
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeachersResponse(
    private var message: String,
    val action: String,
    val id: String,
    val statusCode: Int = 0,
    val teachers: ArrayList<User>,
    val items: ArrayList<User>
) : Parcelable