package com.example.photofilters.data.localdb

import android.graphics.Bitmap
import androidx.room.*
import io.reactivex.Single


@Dao
interface PhotoFiltersDao {
    @Query("SELECT * FROM photofilters_table ORDER BY overlay_id ASC")
    fun getAll(): Single<List<PhotoFiltersEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(photofiltersData: List<PhotoFiltersEntity>)

    @Query("UPDATE photofilters_table SET overlay_image_bitmap=:bitmap WHERE overlay_id = :overlayId")
    fun update(overlayId: Int, bitmap: Bitmap)
}