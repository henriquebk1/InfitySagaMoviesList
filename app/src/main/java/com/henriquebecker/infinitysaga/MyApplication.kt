package com.henriquebecker.infinitysaga

import androidx.multidex.MultiDexApplication
import com.henriquebecker.infinitysaga.di.*
import com.henriquebecker.infinitysaga.util.Prefs
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


val prefs: Prefs by lazy {
    MyApplication.prefs!!
}

class MyApplication : MultiDexApplication() {

    companion object {
        var prefs: Prefs? = null
    }

    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(this)
        startKoin {
            androidLogger()

            androidContext(this@MyApplication)

            modules(
                listOf(
                    apiModule,
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }
}