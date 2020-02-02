package ru.vlabum.cplustest.model.repo

import io.reactivex.Single
import ru.vlabum.cplustest.model.entity.IItem
import ru.vlabum.cplustest.model.entity.room.RoomItem

class RoomStorage : IStorage {
    override fun loadItems(): Single<List<IItem>> {
        return Single.create {emitter ->
            //TODO: завязать на Room
            val items: List<RoomItem> = arrayListOf(
                RoomItem("name1", "name1", "/storage/emulated/0/Download/IMG_8329_2.JPG"),
                RoomItem("name2", "name2", "/storage/emulated/0/Download/IMG_8329_2.JPG"),
                RoomItem("name3", "name3", "/storage/emulated/0/Download/IMG_8329_2.JPG"),
                RoomItem("name4", "name4", "/storage/emulated/0/Download/IMG_8329_2.JPG"),
                RoomItem("name5", "name5", "/storage/emulated/0/Download/IMG_8329_2.JPG"),
                RoomItem("name6", "name6", "/storage/emulated/0/Download/IMG_8329_2.JPG")
                )
            emitter.onSuccess(items)
        }
    }
}