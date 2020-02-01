package ru.vlabum.cplustest.model.repo

import io.reactivex.Single
import ru.vlabum.cplustest.model.entity.IItem
import ru.vlabum.cplustest.model.entity.room.RoomItem

class RoomStorage : IStorage {
    override fun loadItems(): Single<List<IItem>> {
        return Single.create {emitter ->
            //TODO: завязать на Room
            val items: List<RoomItem> = arrayListOf(
                RoomItem(1, "name1"),
                RoomItem(2, "name2"),
                RoomItem(3, "name3"),
                RoomItem(4, "name4"),
                RoomItem(5, "name5"),
                RoomItem(6, "name6")
                )
            emitter.onSuccess(items)
        }
    }
}