package com.example.photofilters.data.localdb

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photofilters_table")
data class PhotoFiltersEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "overlay_id") val id: Int,
    @ColumnInfo(name = "overlay_name") val name: String,
    @ColumnInfo(name = "overlay_preview_image") val icon: String,
    @ColumnInfo(name = "overlay_image") var image: String,
    @ColumnInfo(name = "overlay_image_bitmap") var imageBitmap: Bitmap?
)