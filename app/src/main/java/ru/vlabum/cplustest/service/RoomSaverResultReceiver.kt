package ru.vlabum.cplustest.service

import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import java.lang.ref.WeakReference

public class RoomSaverResultReceiver(handler: Handler) : ResultReceiver(handler) {

    companion object {
        val RESULT_CODE_OK = 1100
        val RESULT_CODE_ERROR = 666
        val PARAM_EXCEPTION = "exception"
        val PARAM_RESULT = "result"

        val PARAM_RECEIVER = "app.result.receiver";
        val PARAM_RECEIVER_DATA = PARAM_RECEIVER + ".data";
    }

    private var weakReference: WeakReference<Receiver>? = null
    private var receiver: Receiver? = null

    fun setReceiver(receiver: Receiver) {
        weakReference = WeakReference(receiver)
    }

    override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
        weakReference?.get()?.onReceiveResult(resultCode, resultData)
    }

    interface Receiver {
        fun onReceiveResult(resultCode: Int, data: Bundle?)
    }
}