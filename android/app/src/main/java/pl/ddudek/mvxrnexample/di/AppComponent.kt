package pl.ddudek.mvxrnexample.di

import android.app.Application
import com.facebook.react.ReactNativeHost
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import pl.ddudek.mvxrnexample.MainApplication
import pl.ddudek.mvxrnexample.view.common.reactnativebridge.AppReactNativeBridge
import javax.inject.Singleton

@Component(modules = [
    NetworkingModule::class,
    DataModule::class,
    ActivitiesModule::class,
    AndroidSupportInjectionModule::class
])
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance fun application(application: Application): Builder
        @BindsInstance fun reactNativeHost(reactNativeHost: ReactNativeHost): Builder
        fun build(): AppComponent
    }

    fun getAppReactNativeBridge(): AppReactNativeBridge

    fun inject(application: MainApplication)
}