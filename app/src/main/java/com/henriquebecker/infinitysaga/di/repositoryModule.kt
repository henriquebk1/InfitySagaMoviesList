package com.henriquebecker.infinitysaga.di

import com.henriquebecker.infinitysaga.data.api.MovieApi
import com.henriquebecker.infinitysaga.data.dao.MovieDao
import com.henriquebecker.infinitysaga.data.repository.MovieRepository
import org.koin.dsl.module

val repositoryModule = module {
    fun provideUserRepository(api: MovieApi, dao: MovieDao): MovieRepository {
        return MovieRepository(api, dao)
    }

    single { provideUserRepository(get(), get()) }
}