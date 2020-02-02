package ru.vlabum.cplustest.extensions

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore


fun Uri.getPath(context: Context): String? {

    val result: String?
    val cursor: Cursor? = context.contentResolver.query(this, null, null, null, null)
    if (cursor == null) { // Source is Dropbox or other similar local file path
        result = this.path
    } else {
        cursor.moveToFirst()
        val idx: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        result = cursor.getString(idx)
        cursor.close()
    }
    return result
}