package com.moddakir.moddakir.utils

import androidx.lifecycle.*
import kotlin.reflect.KFunction0

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this, Observer { it?.let { t -> action(t) } })
}
