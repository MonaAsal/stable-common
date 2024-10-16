package com.moddakir.moddakir.utils.socket

import android.util.Log
import com.example.moddakirapps.BuildConfig
import com.moddakir.moddakir.App
import com.moddakir.moddakir.utils.AccountPreference
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import io.socket.engineio.client.transports.Polling
import io.socket.engineio.client.transports.WebSocket
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber

class SocketClientListener(socketTeacherInterface: SocketTeacherInterface?) {
    companion object{
        @Volatile
        var socketClient: SocketClientListener? = null
        var msocketTeacherInterface: SocketTeacherInterface? = null
        @Synchronized
        fun InitInstance(socketTeacherInterface: SocketTeacherInterface?): SocketClientListener? {
            if (socketClient == null) {
                socketClient = SocketClientListener(socketTeacherInterface)
            }
            return socketClient
        }
    }

    private var socket: Socket? = null
     var isConnected: Boolean = false


    init {
        msocketTeacherInterface = socketTeacherInterface
        try {
            val options: IO.Options =
                IO.Options.builder().setTransports(arrayOf<String>(WebSocket.NAME, Polling.NAME))
                    .build()

            socket = IO.socket(BuildConfig.SOCKET_URL, options)

            socket!!.on(
                Socket.EVENT_CONNECT,
                Emitter.Listener { _: Array<Any?>? ->
                    if (socketClient != null) {
                        socketClient!!.isConnected = true
                        socketClient!!.onOpen()
                    }
                }).on("message") { args: Array<Any> ->
                isConnected = true
                onMessage(args[0].toString())
            }.on(
                Socket.EVENT_DISCONNECT
            ) { _: Array<Any?>? ->
                isConnected = false
            }
            socket!!.connect()
        } catch (exception: Exception) {
            Timber.i("InitInstance: EVENT_DISCONNECT%s", exception.message)
        }
    }

    fun closeSocket() {
        try {
            if (socketClient != null) {
                socketClient!!.socket!!.disconnect()
                socketClient!!.isConnected = false
                socketClient = null
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }


    fun reConnect() {
        isConnected = false
    }

    private fun onMessage(text: String) {

    }


    private fun onOpen() {
        val jsonObject = JSONObject()
        if (AccountPreference.getUser() != null) {
            var id = ""
            try {
                id = AccountPreference.getUser()!!.id
                if (id == null) {
                    return
                }
                jsonObject.put("type", App.AppName)
                jsonObject.put("data", id)
                if (socketClient!!.socket != null) {
                    socketClient!!.socket!!.emit("message", jsonObject)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }


        isConnected = true
    }


}