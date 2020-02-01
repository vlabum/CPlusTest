package ru.vlabum.cplustest.model.entity

open class Item(
    private var id: Int,
    private var name: String,
    private var description: String? = null,
    private var photoPath: String? = null
) : IItem {

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


    override fun setPhotoPath(uri: String?) {
        this.photoPath = uri
    }

    override fun getPhotoPath(): String? = photoPath

}