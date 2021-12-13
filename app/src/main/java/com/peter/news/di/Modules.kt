package com.peter.news.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.peter.news.R
import com.peter.news.network.ApiService
import com.peter.news.pojo.Article
import com.peter.news.ui.details.DetailsViewModel
import com.peter.news.ui.main.NewsAdapter
import com.peter.news.ui.main.NewsViewModel
import com.peter.news.ui.main.OnClickListener
import com.peter.news.ui.main.PostDataSource
import io.ktor.http.*
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val newsViewModelModule = module {
    viewModel { NewsViewModel(get(), get()) }
}

val detailsViewModelModule = module {
    viewModel { DetailsViewModel(get()) }
}

val retrofitModule = module {

    single {
        retrofit(androidApplication().getString(R.string.base_url))
    }
    single {
        get<Retrofit>().create(ApiService::class.java)
    }
}

private fun retrofit(baseUrl: String) = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(baseUrl)
    .build()