package ru.vlabum.cplustest.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.ContentProviderKey
import ru.vlabum.cplustest.App
import ru.vlabum.cplustest.presenter.MainPresenter
import ru.vlabum.cplustest.service.RoomSaverService
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class])
interface AppComponent {

//    @Component.Builder
//    abstract class Builder : AndroidInjector.Builder<App>(){
//    }

    fun inject(mainPresenter: MainPresenter)
    fun inject(service: RoomSaverService)

}