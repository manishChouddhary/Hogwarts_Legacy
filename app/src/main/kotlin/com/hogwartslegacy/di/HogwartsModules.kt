package com.hogwartslegacy.di

import com.hogwartslegacy.BuildConfig
import com.hogwartslegacy.core.HogwartsCore
import com.hogwartslegacy.core.HogwartsRepository
import com.hogwartslegacy.core.data.local.HogwartsLocalDataStore
import com.hogwartslegacy.core.data.local.LocalDataSource
import com.hogwartslegacy.core.data.remote.HogwartsService
import com.hogwartslegacy.core.data.remote.RemoteDataSource
import com.hogwartslegacy.core.data.remote.RetrofitHogwartsDataSource
import com.hogwartslegacy.core.delegate.HogwartsDelegate
import com.hogwartslegacy.core.delegate.OfflineFirstRepository
import com.hogwartslegacy.presentation.CharacterListViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val hogwartsModule = module {
    single {
        OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>().create(HogwartsService::class.java) }
    factory<RemoteDataSource> { RetrofitHogwartsDataSource(get()) }
    single<LocalDataSource> { HogwartsLocalDataStore() }
    single<HogwartsRepository> { OfflineFirstRepository(get(), get()) }
    single<HogwartsCore> { HogwartsDelegate(get()) }
    viewModel { CharacterListViewModel(get()) }
}