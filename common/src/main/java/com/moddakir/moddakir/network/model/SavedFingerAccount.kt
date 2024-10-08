package com.moddakir.moddakir.network.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SavedFingerAccount(
    val userName: String,
    val password: String
): Parcelable