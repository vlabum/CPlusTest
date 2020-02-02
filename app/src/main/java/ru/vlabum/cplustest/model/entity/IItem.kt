package ru.vlabum.cplustest.model.entity

interface IItem {

    fun setId(id: String)
    fun getId(): String

    fun setName(name: String)
    fun getName(): String

    fun setDescription(description: String?)
    fun getDescription(): String?

    fun setImagePath(imagePath: String?)
    fun getImagePath(): String?

}
