package com.moddakir.moddakir.network.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item(

    var id: String,
    val title: String,
    var content: String,
    val number: String,
    var date: String,
) : Parcelable