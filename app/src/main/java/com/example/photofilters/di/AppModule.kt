package com.example.photofilters.di

import com.example.photofilters.data.api.ApiServices
import com.example.photofilters.data.api.PhotoFiltersApi
import com.example.photofilters.data.repositories.PhotoFiltersRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Singleton
    @Provides
    fun provideApi(): PhotoFiltersApi = ApiServices.getClient()

    @Provides
    fun providePhotoFiltersRepository() = PhotoFiltersRepository()
}