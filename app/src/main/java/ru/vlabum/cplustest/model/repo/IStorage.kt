package ru.vlabum.cplustest.model.repo

import io.reactivex.Single
import ru.vlabum.cplustest.model.entity.IItem

interface IStorage {
    fun loadItems(): Single<List<IItem>>
}