package ru.vlabum.cplustest.model.entity.database

import ru.vlabum.cplustest.model.entity.IItem

interface IDatabase {
    fun getItem(name: String): IItem?
    fun getItemList(): List<IItem>?
    fun saveItem(item: IItem)
}