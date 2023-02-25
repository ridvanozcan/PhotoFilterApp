package com.example.photofilters.data.repositories

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.photofilters.PhotoFiltersApplication
import com.example.photofilters.data.api.PhotoFiltersApi
import com.example.photofilters.data.models.PhotoFiltersModel
import com.example.photofilters.data.models.toDataEntityList
import com.example.photofilters.data.models.toDataList
import com.example.photofilters.di.DaggerAppComponent
import com.example.photofilters.utils.subscribeOnBackground
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import javax.inject.Inject

class PhotoFiltersRepository {

    @Inject
    lateinit var photoFiltersApi: PhotoFiltersApi

    init {
        DaggerAppComponent.create().inject(this)
    }

    private val _photoFiltersData by lazy { MutableLiveData<List<PhotoFiltersModel>>() }
    val photoFiltersData: LiveData<List<PhotoFiltersModel>>
        get() = _photoFiltersData


    private val _isInProgress by lazy { MutableLiveData<Boolean>() }
    val isInProgress: LiveData<Boolean>
        get() = _isInProgress

    private val _isError by lazy { MutableLiveData<Boolean>() }
    val isError: LiveData<Boolean>
        get() = _isError

    private fun insertData(): Disposable {
        return photoFiltersApi.getPhotoFilters()
            .subscribeOn(Schedulers.io())
            .subscribeWith(subscribeToDatabase())
    }

    private fun subscribeToDatabase(): DisposableSubscriber<List<PhotoFiltersModel>> {
        return object : DisposableSubscriber<List<PhotoFiltersModel>>() {

            override fun onNext(photoFiltersResult: List<PhotoFiltersModel>?) {
                if (photoFiltersResult != null) {
                    val entityList = photoFiltersResult.toList().toDataEntityList()
                    PhotoFiltersApplication.database.apply {
                        photoFiltersDao().insert(entityList)
                    }
                }
            }

            override fun onError(t: Throwable?) {
                _isInProgress.postValue(true)
                Log.e("insertData()", "TrendingResult error: ${t?.message}")
                _isError.postValue(true)
                _isInProgress.postValue(false)
            }

            override fun onComplete() {
                Log.v("insertData()", "insert success")
                getAllDataDb()
            }
        }
    }

    fun updateBitmap(overlayId: Int, bitmap: Bitmap) {
        subscribeOnBackground {
            PhotoFiltersApplication.database.photoFiltersDao().update(overlayId, bitmap)
        }
    }

    private fun getAllDataDb(): Disposable {
        return PhotoFiltersApplication.database.photoFiltersDao()
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { dataEntityList ->
                    _isInProgress.postValue(true)
                    if (dataEntityList != null && dataEntityList.isNotEmpty()) {
                        _isError.postValue(false)
                        _photoFiltersData.postValue(dataEntityList.toDataList())
                    } else {
                        insertData()
                    }
                    _isInProgress.postValue(false)
                },
                {
                    _isInProgress.postValue(true)
                    _isError.postValue(true)
                    _isInProgress.postValue(false)
                }
            )
    }

    fun fetchPhotoFiltersFromDatabase(): Disposable = getAllDataDb()
}