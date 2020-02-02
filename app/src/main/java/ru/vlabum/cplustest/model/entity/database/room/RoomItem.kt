package ru.vlabum.cplustest.model.entity.database.room

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.vlabum.cplustest.model.entity.IItem

@Entity
class RoomItem() : IItem {

    constructor(id: String, name: String, imagePath: String? = null) : this() {
        this.id = id
        this.name = name
        this.imagePath = imagePath
    }

    @NonNull
    @PrimaryKey
    internal lateinit var id: String

    internal lateinit var name: String

    internal var description: String? = null

    internal var imagePath: String? = null


    override fun setId(id: String) {
        this.id = id
    }

    override fun getId(): String = id


    override fun setName(name: String) {
        this.name = name
    }

    override fun getName(): String = name


    override fun setDescription(description: String?) {
        this.description = description
    }

    override fun getDescription(): String? = description


    override fun setImagePath(path: String?) {
        this.imagePath = path
    }

    override fun getImagePath(): String? = imagePath

}