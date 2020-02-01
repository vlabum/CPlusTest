package ru.vlabum.cplustest.model.entity.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.vlabum.cplustest.model.entity.room.dao.ItemDao
import java.lang.RuntimeException

@androidx.room.Database(entities = [RoomItem::class], version = 1)
abstract class Database : RoomDatabase() {

    companion object {
        private val DB_NAME = "CPlusTest.db"

        @Volatile
        private lateinit var instance: Database

        fun create(сontext: Context) {
            instance = Room.databaseBuilder(
                сontext.applicationContext,
                Database::class.java,
                DB_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }

        @Synchronized
        fun getInstance(): Database {
            if (instance == null) {
                throw RuntimeException("Need call create() method.")
            }
            return instance
        }
    }

    abstract fun getItemDao(): ItemDao

}