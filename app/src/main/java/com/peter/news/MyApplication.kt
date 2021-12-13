package com.peter.news

import android.app.Application
import com.peter.news.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(listOf(retrofitModule, newsViewModelModule, detailsViewModelModule))
        }
    }
}