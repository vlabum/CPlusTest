package ru.vlabum.cplustest.view

import android.net.Uri
import java.security.cert.CertPath

interface IItemRowView {
    fun getPos(): Int
    fun setName(name: String)
    fun setImage(path: String?)
}