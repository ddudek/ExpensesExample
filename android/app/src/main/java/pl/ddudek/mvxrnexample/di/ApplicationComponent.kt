package pl.ddudek.mvxrnexample.di

import com.google.gson.GsonBuilder
import pl.ddudek.mvxrnexample.networking.ExpensesApi
import pl.ddudek.mvxrnexample.view.common.reactnativebridge.AppReactNativeBridge
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApplicationComponent {

    val api: ExpensesApi

    val bridgeCallbackListeners = AppReactNativeBridge()

    constructor() {
        val gson = GsonBuilder()
                .setLenient()
                .create()

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        api = retrofit.create(ExpensesApi::class.java)
    }
}