package ru.vlabum.cplustest.model.entity.database.room

import ru.vlabum.cplustest.model.entity.IItem
import ru.vlabum.cplustest.model.entity.database.IDatabase

class RoomDatabase : IDatabase {
    override fun getItem(name: String): IItem? {
        return Database.getInstance().getItemDao().findByName(name)
    }

    override fun getItemList(): List<IItem>? {
        return Database.getInstance().getItemDao().getAll()
    }

    override fun saveItem(item: IItem) {
        // при совпадении замена: @Insert(onConflict = OnConflictStrategy.REPLACE)
        var roomItem: RoomItem = RoomItem()
        roomItem.id = item.getId()
        roomItem.name = item.getName()
        roomItem.description = item.getDescription()
        roomItem.imagePath = item.getImagePath()

        Database.getInstance().getItemDao().insert(roomItem)
    }
}