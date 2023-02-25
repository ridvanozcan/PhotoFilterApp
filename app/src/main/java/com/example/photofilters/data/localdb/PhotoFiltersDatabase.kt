package com.example.photofilters.data.localdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.photofilters.utils.Converter
import com.example.photofilters.utils.DATABASE_NAME

@Database(entities = [PhotoFiltersEntity::class], version = 2)
@TypeConverters(Converter::class)
abstract class PhotoFiltersDatabase : RoomDatabase() {

    abstract fun photoFiltersDao(): PhotoFiltersDao

    companion object {
        @Volatile // All threads have immediate access to this property
        private var instance: PhotoFiltersDatabase? = null

        private val LOCK = Any() // Makes sure no threads making the same thing at the same time

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                PhotoFiltersDatabase::class.java,
                DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }
    }
}