package com.example.photofilters.data.api

import com.example.photofilters.data.models.PhotoFiltersModel
import io.reactivex.Flowable
import retrofit2.http.GET

interface PhotoFiltersApi {

    @GET("overlay.json")
    fun getPhotoFilters(): Flowable<List<PhotoFiltersModel>>

}