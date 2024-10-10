package com.moddakir.moddakir.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moddakir.moddakir.network.Resource
import com.moddakir.moddakir.network.model.AboutModel
import com.moddakir.moddakir.network.model.TicketReplyResponse
import com.moddakir.moddakir.network.model.base.BaseViewModel
import com.moddakir.moddakir.network.model.response.ModdakirResponse
import com.moddakir.moddakir.network.model.response.ResponseModel
import com.moddakir.moddakir.network.model.response.TicketResponse
import com.moddakir.moddakir.network.model.response.TicketsRepliesResponse
import com.moddakir.moddakir.network.model.response.TicketsResponse
import com.moddakir.moddakir.useCase.StaticPagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StaticPagesViewModel @Inject constructor(
    private val staticPagesUseCase: StaticPagesUseCase
) : BaseViewModel() {
    private val aboutUsMutableLiveData = MutableLiveData<Resource<ModdakirResponse<AboutModel>>>()
    val aboutUsLiveData: MutableLiveData<Resource<ModdakirResponse<AboutModel>>> = aboutUsMutableLiveData

    private val contactUsMutableLiveData = MutableLiveData<Resource<ModdakirResponse<ResponseModel>>>()
    val contactUsLiveData: MutableLiveData<Resource<ModdakirResponse<ResponseModel>>> = contactUsMutableLiveData

    private val historyMutableLiveData = MutableLiveData<Resource<ModdakirResponse<TicketsResponse>>>()
    val historyLiveData: MutableLiveData<Resource<ModdakirResponse<TicketsResponse>>> = historyMutableLiveData

    private val sendReplayMutableLiveData = MutableLiveData<Resource<ModdakirResponse<TicketReplyResponse>>>()
    val sendReplayLiveData: MutableLiveData<Resource<ModdakirResponse<TicketReplyResponse>>> = sendReplayMutableLiveData

    private val ticketsReplaysMutableLiveData = MutableLiveData<Resource<ModdakirResponse<TicketsRepliesResponse>>>()
    val ticketsReplaysLiveData: MutableLiveData<Resource<ModdakirResponse<TicketsRepliesResponse>>> = ticketsReplaysMutableLiveData

    private val ticketByIdMutableLiveData = MutableLiveData<Resource<ModdakirResponse<TicketResponse>>>()
    val ticketByIdLiveData: MutableLiveData<Resource<ModdakirResponse<TicketResponse>>> = ticketByIdMutableLiveData


    fun getAboutUs() {
        viewModelScope.launch {
            staticPagesUseCase.getAboutUs()
        }
    }
    fun contactUsForm(message:String,title:String) {
        viewModelScope.launch {
            staticPagesUseCase.contactUsForm(message,title)
        }
    }

    fun getListContactUs(page:Int) {
        viewModelScope.launch {
            staticPagesUseCase.getListContactUs(page)
        }
    }

    fun sendReplay(message:String,ticketId:String) {
        viewModelScope.launch {
            staticPagesUseCase.sendReplay(message,ticketId)
        }
    }


    fun getTicketReplies(page:Int,messageId:String) {
        viewModelScope.launch {
            staticPagesUseCase.getTicketReplies(page,messageId)
        }
    }

    fun getTicketById(ticketId:String) {
        viewModelScope.launch {
            staticPagesUseCase.getTicketById(ticketId)
        }
    }




}