package ru.vlabum.cplustest.model.entity

open class Item(
    private var id: String,
    private var name: String,
    private var description: String? = null,
    private var imagePath: String? = null
) : IItem {

    override fun setId(id: String) {
        setName(id)
    }

    override fun getId(): String = id


    override fun setName(name: String) {
        this.id = name
        this.name = name
    }

    override fun getName(): String = name


    override fun setDescription(description: String?) {
        this.description = description
    }

    override fun getDescription(): String? = description


    override fun setImagePath(imagePath: String?) {
        this.imagePath = imagePath
    }

    override fun getImagePath(): String? = imagePath

}