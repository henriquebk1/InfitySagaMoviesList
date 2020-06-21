package com.henriquebecker.infinitysaga.di

import com.henriquebecker.infinitysaga.viewmodel.MovieViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { MovieViewModel(get(), get()) }
}