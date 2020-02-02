package ru.vlabum.cplustest

import android.app.Application
import android.os.Handler
import ru.vlabum.cplustest.service.RoomSaverResultReceiver


class App : Application() {

    companion object {

        val ROOM_JOB_ID = 1010

        val ITEM_NAME = "item_name"
        val ITEM_DESCRIPTION = "item_description"
        val ITEM_IMAGE_PATH = "item_image_path"

        private lateinit var instance: App
        fun getInstance(): App {
            return instance
        }
    }

    //TODO: попробовать добавить абстракцию и воткнуть в перзенер
    lateinit var resultReceiver: RoomSaverResultReceiver


    override fun onCreate() {
        super.onCreate()
        instance = this
        resultReceiver = RoomSaverResultReceiver(Handler())
//        Database.create(this)
//        appComponent = DaggerAppComponent.builder().build()
//        appComponent.inject(this)
    }

}