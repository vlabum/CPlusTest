package ru.vlabum.cplustest.model.entity.room.dao

import androidx.room.*
import ru.vlabum.cplustest.model.entity.room.RoomItem

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(item: RoomItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg item: RoomItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(item: List<RoomItem>)

    @Update
    abstract fun update(item: RoomItem)

    @Update
    abstract fun update(vararg item: RoomItem)

    @Update
    abstract fun update(item: List<RoomItem>)

    @Delete
    abstract fun delete(item: RoomItem)

    @Delete
    abstract fun delete(vararg item: RoomItem)

    @Delete
    abstract fun delete(item: List<RoomItem>)

    @Query("SELECT * FROM RoomItem")
    abstract fun getAll(): List<RoomItem>

    @Query("SELECT * FROM RoomItem WHERE name = :name LIMIT 1")
    abstract fun findByUrl(name: String): RoomItem

    @Query("SELECT 1 as isExists FROM RoomItem WHERE name = :name LIMIT 1")
    abstract fun contains(name: String): Int

    @Query("DELETE FROM RoomItem")
    abstract fun clear()

}