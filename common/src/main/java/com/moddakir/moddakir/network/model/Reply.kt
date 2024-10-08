package com.moddakir.moddakir.network.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Reply(
    var id: String? = null,
    var content: String? = null,
    var date: String? = null,
    var sender: Sender? = null,
    var file: String? = null,
    var fileType: String? = null,
) : Parcelable