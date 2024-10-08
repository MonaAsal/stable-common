package com.moddakir.moddakir.network.model.base


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moddakir.moddakir.network.model.base.error.ErrorManager
import com.moddakir.moddakir.network.model.base.error.ErrorMapper
import com.moddakir.moddakir.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {

    open val errorManager: ErrorManager = ErrorManager(ErrorMapper())

    private val showToastPrivate = MutableLiveData<Event<Any>>()


    fun showToastMessage(message: String) {
        showToastPrivate.value = Event(message)
    }





}
