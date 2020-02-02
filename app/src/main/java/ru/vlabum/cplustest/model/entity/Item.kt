package ru.vlabum.cplustest.model.entity

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable

open class Item(
    private var id: String,
    private var name: String,
    private var description: String? = null,
    private var imagePath: String? = null
) : IItem, Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString(),
        parcel.readString()
    ) {
    }

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

    fun toBundle(): Bundle {
        val args = Bundle(1)
        args.putStringArray(ARGS_BUNDLE, arrayOf(id, name, description, imagePath))
        return args
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(id)
        dest?.writeString(name)
        dest?.writeString(description)
        dest?.writeString(imagePath)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(parcel: Parcel): Item {
            return Item(parcel)
        }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }

        internal val ARGS_BUNDLE = Item::class.java.name + ":Bundle"
        internal val ARGS_BUNDLE_LIST = Item::class.java.name + ":Bundle_LIST"

        fun fromBundle(bundle: Bundle): Item {
            val spec = bundle.getStringArray(ARGS_BUNDLE)
            return Item(spec!![0], spec[1], spec[2], spec[3])
        }
    }
}