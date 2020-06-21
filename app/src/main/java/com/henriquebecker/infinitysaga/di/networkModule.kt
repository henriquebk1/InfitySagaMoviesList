package com.henriquebecker.infinitysaga.di

import android.util.Log
import com.google.gson.GsonBuilder
import com.henriquebecker.infinitysaga.BuildConfig
import com.henriquebecker.infinitysaga.util.CalendarDeserializer
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

val networkModule = module {
    single { Cache(androidApplication().cacheDir, 10L * 1024 * 1024) }

    single { GsonBuilder()
        .registerTypeHierarchyAdapter(Calendar::class.java, CalendarDeserializer())
        .create()
    }

    single {
        OkHttpClient.Builder().apply {
            cache(get())
            retryOnConnectionFailure(true)
            addInterceptor(HttpLoggingInterceptor { message ->
                Log.d("API",message)
            }.apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else{
                    HttpLoggingInterceptor.Level.NONE
                }

            })
        }.build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://private-b34167-rvmarvel.apiary-mock.com")
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get())
            .build()
    }

}
