package com.henriquebecker.infinitysaga.di

import com.henriquebecker.infinitysaga.data.api.MovieApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single { get<Retrofit>().create(MovieApi::class.java) }
}