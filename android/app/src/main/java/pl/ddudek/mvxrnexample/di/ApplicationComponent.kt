package pl.ddudek.mvxrnexample.di

import com.facebook.react.ReactNativeHost
import com.google.gson.GsonBuilder
import pl.ddudek.mvxrnexample.data.cache.ExpensesMemoryCache
import pl.ddudek.mvxrnexample.networking.API_URL
import pl.ddudek.mvxrnexample.networking.ExpensesApi
import pl.ddudek.mvxrnexample.view.common.reactnativebridge.AppReactNativeBridge
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApplicationComponent(reactNativeHost: ReactNativeHost) {

    val api: ExpensesApi

    val reactNativeBridge = AppReactNativeBridge(reactNativeHost)

    val expensesMemoryCache = ExpensesMemoryCache()

    init {
        val gson = GsonBuilder()
                .setLenient()
                .create()

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        api = retrofit.create(ExpensesApi::class.java)
    }
}