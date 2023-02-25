package com.example.photofilters

import android.app.Application
import com.example.photofilters.data.localdb.PhotoFiltersDatabase

class PhotoFiltersApplication : Application() {

    companion object {
        lateinit var instance: PhotoFiltersApplication
        lateinit var database: PhotoFiltersDatabase
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        database = PhotoFiltersDatabase.invoke(this)
    }

}