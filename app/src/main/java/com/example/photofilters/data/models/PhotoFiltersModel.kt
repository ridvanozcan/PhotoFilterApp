package com.example.photofilters.data.models

import android.graphics.Bitmap

data class PhotoFiltersModel(
    val overlayId: Int,
    val overlayName: String,
    val overlayPreviewIconUrl: String,
    val overlayUrl: String,
    var overlayImageBitMap: Bitmap?
)
