package ru.vlabum.cplustest.di

import dagger.Module
import dagger.Provides
import ru.vlabum.cplustest.model.entity.database.IDatabase
import ru.vlabum.cplustest.model.entity.database.room.RoomDatabase
import ru.vlabum.cplustest.model.repo.IStorage
import ru.vlabum.cplustest.model.repo.RoomStorage
import javax.inject.Singleton

@Module
open class DatabaseModule {

    @Singleton
    @Provides
    open fun roomDatabase(): IDatabase {
        return RoomDatabase()
    }

    @Singleton
    @Provides
    open fun roomStorage(roomDatabase: IDatabase): IStorage {
        return RoomStorage(roomDatabase)
    }

}