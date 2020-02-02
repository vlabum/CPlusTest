package ru.vlabum.cplustest.model.repo

import io.reactivex.Single
import ru.vlabum.cplustest.model.entity.IItem
import ru.vlabum.cplustest.model.entity.database.IDatabase

class RoomStorage(private val database: IDatabase) : IStorage {
    override fun loadItems(): Single<List<IItem>> {
        return Single.create {emitter ->

            var items = database.getItemList()
            if (items == null) items = listOf()

            emitter.onSuccess(items)
        }
    }
}