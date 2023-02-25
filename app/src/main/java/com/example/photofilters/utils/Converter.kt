package com.example.photofilters.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class Converter {

    @TypeConverter
    fun toBitmap(bytes: ByteArray?): Bitmap? {
        return if (bytes == null)
                    null
                else
                    return BitmapFactory.decodeByteArray(
                        bytes, 0, bytes.size)
    }

    @TypeConverter
    fun fromBitmap(bmp: Bitmap?): ByteArray? {
        return if (bmp == null) {
            null
        } else {
            val outputStream = ByteArrayOutputStream()
            bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.toByteArray()
        }
    }
}