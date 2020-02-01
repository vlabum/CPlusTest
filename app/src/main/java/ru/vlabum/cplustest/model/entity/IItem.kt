package ru.vlabum.cplustest.model.entity

interface IItem {

    fun setId(id: Int)
    fun getId(): Int

    fun setName(name: String)
    fun getName(): String

    fun setDescription(description: String?)
    fun getDescription(): String?

    fun setPhotoPath(path: String?)
    fun getPhotoPath(): String?

}
