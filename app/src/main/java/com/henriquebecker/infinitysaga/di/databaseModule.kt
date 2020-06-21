package com.henriquebecker.infinitysaga.di

import android.app.Application
import androidx.room.Room
import com.henriquebecker.infinitysaga.data.AppDatabase
import com.henriquebecker.infinitysaga.data.dao.MovieDao
import com.henriquebecker.infinitysaga.data.datasource.MovieDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "infinitysaga.db")
            .fallbackToDestructiveMigration()
            .build()
    }


    fun provideDao(database: AppDatabase): MovieDao {
        return database.movieDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
    single { MovieDataSource(get()) }
}
