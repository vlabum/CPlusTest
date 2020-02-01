package ru.vlabum.cplustest.presenter

import io.reactivex.subjects.PublishSubject
import ru.vlabum.cplustest.model.entity.IItem
import ru.vlabum.cplustest.view.IItemRowView

interface IItemListPresenter {
    var items: ArrayList<IItem>
    fun bind(viewI: IItemRowView)
    fun getCount(): Int
    fun getClickSubject(): PublishSubject<IItemRowView>
}