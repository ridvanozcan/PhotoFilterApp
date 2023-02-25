package com.example.photofilters.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Environment
import android.view.View
import android.widget.Toast
import java.io.*

fun saveImage(context: Context, view: View) {
    val bitmap = Bitmap.createBitmap(
        view.width, view.height, Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    view.draw(canvas)
    getImageUriFromBitmap(context, bitmap)
}

private fun getImageUriFromBitmap(context: Context, bitmap: Bitmap?): Uri {
    var filePath = ""
    try {
        filePath = createAndGetFilePath(context)
        val fileOutputStream = FileOutputStream(filePath)
        val bos = BufferedOutputStream(fileOutputStream)
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, bos)
        bos.flush()
        bos.close()
        Toast.makeText(context, "The picture has been successfully saved.",
            Toast.LENGTH_SHORT).show()
    } catch (e: FileNotFoundException) {

    } catch (e: IOException) {

    }
    return Uri.parse(filePath)
}

private fun createAndGetFilePath(context: Context): String {
    val file = File(
        Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DCIM
        ), "Camera"
    )
    try {
        if (!file.exists()) {
            file.mkdirs()
        }
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
    return file.absolutePath + "/photofilters" + System.currentTimeMillis() + ".jpg"
}