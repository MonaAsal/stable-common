package com.moddakir.moddakir.utils.socket

import com.moddakir.moddakir.network.model.User

interface SocketTeacherInterface {
    fun getTeacherModelFromSocket(teacher: User?)
}