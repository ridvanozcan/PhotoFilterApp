package com.example.photofilters.viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.photofilters.data.models.PhotoFiltersModel
import com.example.photofilters.data.repositories.PhotoFiltersRepository
import com.example.photofilters.di.DaggerAppComponent
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class PhotoFiltersViewModel : ViewModel() {
    @Inject
    lateinit var repository: PhotoFiltersRepository

    private val compositeDisposable by lazy { CompositeDisposable() }

    private val _selectedBitmap by lazy { MutableLiveData<PhotoFiltersModel>() }
    val selectedBitmap: LiveData<PhotoFiltersModel>
        get() = _selectedBitmap

    init {
        DaggerAppComponent.create().inject(this)
        compositeDisposable.add(repository.fetchPhotoFiltersFromDatabase())
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun getPhotoFiltersFromDb(overlayId: Int, bitmap: Bitmap) {
        repository.updateBitmap(overlayId, bitmap)
    }

    suspend fun updateBitmapDb(context: Context, arrayList: ArrayList<PhotoFiltersModel>) {
        for (item in arrayList) {
            getPhotoFiltersFromDb(item.overlayId, getBitmap(context, item.overlayUrl))
        }
    }

    suspend fun getBitmap(context:Context, url: String): Bitmap {
        val loading = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(url)
            .build()

        val result = (loading.execute(request) as SuccessResult).drawable
        return Bitmap.createScaledBitmap((result as BitmapDrawable).bitmap, 2500, 2500, true)
    }

}