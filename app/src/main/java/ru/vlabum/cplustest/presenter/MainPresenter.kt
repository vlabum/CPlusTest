package ru.vlabum.cplustest.presenter

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import ru.vlabum.cplustest.model.entity.IItem
import ru.vlabum.cplustest.model.entity.Item
import ru.vlabum.cplustest.model.repo.IStorage
import ru.vlabum.cplustest.view.IItemRowView
import ru.vlabum.cplustest.view.IMainView
import javax.inject.Inject

@InjectViewState
open class MainPresenter(val mainThread: Scheduler) : MvpPresenter<IMainView>() {

    val TAG = "CPLUS_MainPresenter"

    var listItems: IItemListPresenter = ItemListPresenter()

    @Inject
    lateinit var storage: IStorage// = RoomStorage(database)

    var disposables = CompositeDisposable()

    var isGrantedPermission = true

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        init()
    }

    private fun init() {
        loadItems()
        disposables.add(
            listItems.getClickSubject().subscribe { row ->
                viewState.showMessage(listItems.items[row.getPos()].getName())
                viewState.openDetails(row.getPos())
            }
        )
    }

    fun onRestart() {
        loadItems()
    }

    fun onPermission(isGranted: Boolean) {
        isGrantedPermission = isGranted
    }

    fun loadItems() {
        viewState.showLoading()
        Log.d(TAG, "loadItems after showLoading")
        disposables.add(
            storage.loadItems()
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread)
                .subscribe(
                    { loadedItems ->
                        Log.d(TAG, "loaded")
                        listItems.items.clear()

                        for (item: IItem in loadedItems) {
                            val it = Item(
                                item.getId(),
                                item.getName(),
                                item.getDescription(),
                                item.getImagePath()
                            )
                            listItems.items.add(it)
                        }

                        viewState.hideLoading()
                        viewState.notifyRV()
                    },
                    { e ->
                        Log.d(TAG, e.message)
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
            viewI.setImage(items[viewI.getPos()].getImagePath())
        }

        override fun getCount(): Int {
            return items.count()
        }

        override fun getClickSubject(): PublishSubject<IItemRowView> {
            return clickSubjectVar
        }
    }

}