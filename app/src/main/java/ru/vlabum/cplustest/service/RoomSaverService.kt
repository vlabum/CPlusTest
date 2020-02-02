package ru.vlabum.cplustest.service

import android.app.IntentService
import android.content.Intent
import android.os.Bundle
import android.os.ResultReceiver
import ru.vlabum.cplustest.App
import ru.vlabum.cplustest.model.entity.Item
import ru.vlabum.cplustest.model.entity.database.IDatabase
import ru.vlabum.cplustest.model.entity.database.room.RoomDatabase

class RoomSaverService : IntentService(RoomSaverService::class.simpleName) {

    val TAG = "CPLUS_RoomSaverService"

    override fun onHandleIntent(intent: Intent?) {

        val receiver: ResultReceiver? = App.getInstance().resultReceiver
        //Thread.sleep(2000)  убрать после отладок

        var id: String? = null
        var name: String? = null
        var description: String? = null
        var imagePath: String? = null

        intent?.let {
            id = intent.getStringExtra(App.ITEM_NAME)
            name = intent.getStringExtra(App.ITEM_NAME)
            description = intent.getStringExtra(App.ITEM_DESCRIPTION)
            imagePath = intent.getStringExtra(App.ITEM_IMAGE_PATH)

            if (id != null && name != null) {
                val database: IDatabase = RoomDatabase()
                database.saveItem(Item(id!!, name!!, description, imagePath))
            }
        }

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