package ru.vlabum.cplustest.service

import android.app.IntentService
import android.content.Intent
import android.os.Bundle
import android.os.ResultReceiver
import android.util.Log
import ru.vlabum.cplustest.App

class RoomSaverService : IntentService(RoomSaverService::class.simpleName) {

    val TAG = "CPLUS_RoomSaverService"

    override fun onHandleIntent(intent: Intent?) {

        val receiver: ResultReceiver? = App.getInstance().resultReceiver
        Thread.sleep(2000) //TODO убрать после отладок
        val id: String? = intent?.getStringExtra(App.ITEM_NAME)
        val name: String? = intent?.getStringExtra(App.ITEM_NAME)
        val description: String? = intent?.getStringExtra(App.ITEM_DESCRIPTION)
        val imagePath: String? = intent?.getStringExtra(App.ITEM_IMAGE_PATH)


        receiver?.let {
            val resultCode: Int
            val bundle = Bundle()
            if (name == null) {
                resultCode = RoomSaverResultReceiver.RESULT_CODE_ERROR
                bundle.putBoolean(RoomSaverResultReceiver.PARAM_RESULT, false)
            } else {
                resultCode = RoomSaverResultReceiver.RESULT_CODE_OK
                bundle.putBoolean(RoomSaverResultReceiver.PARAM_RESULT, true)
            }
            it.send(resultCode, bundle)
        }
    }
}