package ru.vlabum.cplustest.ui.util

import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.ImageView
import java.io.File

fun setImage(path: String, imageView: ImageView ) {
    try {

        val selectedImage: Uri = Uri.fromFile(File(path))
        //TODO попробовать через Glide
        if (Build.VERSION.SDK_INT < 28) {
            val bitmap = MediaStore.Images.Media.getBitmap(
                imageView.context.contentResolver,
                selectedImage
            )
            imageView.setImageBitmap(bitmap)
        } else {
            val source = ImageDecoder.createSource(imageView.context.contentResolver, selectedImage)
            val bitmap = ImageDecoder.decodeBitmap(source)
            imageView.setImageBitmap(bitmap)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

}