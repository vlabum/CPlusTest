package ru.vlabum.cplustest.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.vlabum.cplustest.service.RoomSaverService

@Module
abstract class ServiceModule {
    @ContributesAndroidInjector(modules = [ServiceModule::class])
    abstract fun getService(): RoomSaverService
}