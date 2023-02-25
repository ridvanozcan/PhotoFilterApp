package com.example.photofilters.di

import com.example.photofilters.data.repositories.PhotoFiltersRepository
import com.example.photofilters.viewmodels.PhotoFiltersViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(repository: PhotoFiltersRepository)

    fun inject(viewModel: PhotoFiltersViewModel)

}