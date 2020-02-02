package ru.vlabum.cplustest.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Scheduler
import ru.vlabum.cplustest.model.entity.IItem
import ru.vlabum.cplustest.model.entity.Item
import ru.vlabum.cplustest.view.IAddItemView
import java.io.FileDescriptor

@InjectViewState
class AddItemPresenter(val mainThread: Scheduler) : MvpPresenter<IAddItemView>() {

    private lateinit var item: IItem

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        item = Item("", "")
    }

    fun saveItem() {
        if (!item.getName().isBlank())
            viewState.saveItem(item.getName(), item.getDescription(), item.getImagePath())
    }

    fun onPaused() {
        saveItem()
    }

    fun onImageSelected(path: String?) {
        item.setImagePath(path)
    }

    fun onNameTyped(name: String) {
        item.setName(name)
    }

    fun onDescriptionTyped(description: String?) {
        item.setDescription(description)
    }

}