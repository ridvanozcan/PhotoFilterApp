package com.example.photofilters.data.models

import com.example.photofilters.data.localdb.PhotoFiltersEntity


fun PhotoFiltersModel.toPhotoFiltersEntity() = PhotoFiltersEntity(
    id = this.overlayId,
    name = this.overlayName,
    icon = this.overlayPreviewIconUrl,
    image = this.overlayUrl,
    imageBitmap = this.overlayImageBitMap
)
fun List<PhotoFiltersModel>.toDataEntityList() = this.map { it.toPhotoFiltersEntity() }

fun PhotoFiltersEntity.toPhotoFilters() = PhotoFiltersModel(
    overlayId = this.id,
    overlayName = this.name,
    overlayPreviewIconUrl = this.icon,
    overlayUrl = this.image,
    overlayImageBitMap = this.imageBitmap
)

fun List<PhotoFiltersEntity>.toDataList() = this.map { it.toPhotoFilters() }
