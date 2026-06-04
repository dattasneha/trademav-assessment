package com.snehadatta.signalviewer.di

import com.snehadatta.data.remote.ApiService
import com.snehadatta.data.remote.MainRepository
import com.snehadatta.signalviewer.ui.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL =
    "https://jsonplaceholder.typicode.com/"

val appModule = module {

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
    }

    single<ApiService> {
        get<Retrofit>()
            .create(ApiService::class.java)
    }

    single {
        MainRepository(get())
    }

    viewModel {
        MainViewModel(get())
    }
}