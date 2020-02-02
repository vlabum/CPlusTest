package ru.vlabum.cplustest

import android.app.Application
import android.os.Handler
import ru.vlabum.cplustest.di.AppComponent
import ru.vlabum.cplustest.di.DaggerAppComponent
import ru.vlabum.cplustest.model.entity.database.room.Database
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

    private lateinit var appComponent: AppComponent

    fun getAppComponent() = appComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        resultReceiver = RoomSaverResultReceiver(Handler())
        appComponent = DaggerAppComponent.builder().build()
        Database.create(this)
//        appComponent.inject(this)
    }

}