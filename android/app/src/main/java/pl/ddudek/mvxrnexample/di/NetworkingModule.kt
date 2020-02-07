package pl.ddudek.mvxrnexample.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import pl.ddudek.mvxrnexample.networking.API_URL
import pl.ddudek.mvxrnexample.networking.ExpensesApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkingModule {

    @Provides
    @Singleton
    internal open fun provideGson() : Gson {
        return GsonBuilder()
                .setLenient()
                .create()
    }

    @Provides
    @Singleton
    internal open fun provideRetrofit(gson: Gson) : Retrofit {
        return Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    internal open fun provideApi(retrofit: Retrofit) : ExpensesApi {
        return retrofit.create(ExpensesApi::class.java)
    }

}