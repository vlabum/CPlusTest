package ru.vlabum.cplustest.model.entity.room

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.vlabum.cplustest.model.entity.IItem

@Entity
class RoomItem() : IItem {

    constructor(id: Int, name: String) : this() {
        this.id = id
        this.name = name
    }

    @NonNull
    @PrimaryKey
    internal var id: Int = 0

    internal lateinit var name: String

    internal var description: String? = null

    internal var photoPath: String? = null


    override fun setId(id: Int) {
        this.id = id
    }

    override fun getId(): Int = id


    override fun setName(name: String) {
        this.name = name
    }

    override fun getName(): String = name


    override fun setDescription(description: String?) {
        this.description = description
    }

    override fun getDescription(): String? = description


    override fun setPhotoPath(path: String?) {
        this.photoPath = path
    }

    override fun getPhotoPath(): String? = photoPath

}