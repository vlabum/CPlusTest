package ru.vlabum.cplustest.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import ru.vlabum.cplustest.model.entity.IItem
import ru.vlabum.cplustest.model.repo.IStorage
import ru.vlabum.cplustest.model.repo.RoomStorage
import ru.vlabum.cplustest.view.IItemRowView
import ru.vlabum.cplustest.view.IMainView

@InjectViewState
open class MainPresenter(val mainThread: Scheduler) : MvpPresenter<IMainView>() {

    var listItems: IItemListPresenter = ItemListPresenter()

    var storage: IStorage = RoomStorage()

    var disposables = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadItems()
        disposables.add(
            listItems.getClickSubject().subscribe { row ->
                viewState.showMessage(listItems.items[row.getPos()].getName())
            }
        )
    }

    override fun detachView(view: IMainView?) {
        super.detachView(view)
        disposables.dispose()
    }

    fun loadItems() {
        viewState.showLoading()
        disposables.add(
            storage.loadItems()
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread)
                .subscribe(
                    { loadedItems ->
                        listItems.items.clear()
                        listItems.items.addAll(loadedItems)
                        viewState.hideLoading()
                    },
                    { e ->
                        viewState.hideLoading()
                        e.printStackTrace()
                    }
                )
        )
    }

    class ItemListPresenter : IItemListPresenter {

        var clickSubjectVar = PublishSubject.create<IItemRowView>()

        override var items: ArrayList<IItem> = arrayListOf()

        override fun bind(viewI: IItemRowView) {
            viewI.setName(items[viewI.getPos()].getName())
            viewI.setImage(items[viewI.getPos()].getPhotoPath())
        }

        override fun getCount(): Int {
            return items.count()
        }

        override fun getClickSubject(): PublishSubject<IItemRowView> {
            return clickSubjectVar
        }
    }

}