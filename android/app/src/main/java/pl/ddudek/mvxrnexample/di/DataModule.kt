package pl.ddudek.mvxrnexample.di

        import com.facebook.react.ReactNativeHost
        import dagger.Module
import dagger.Provides
import pl.ddudek.mvxrnexample.data.cache.ExpensesMemoryCache
import pl.ddudek.mvxrnexample.view.common.reactnativebridge.AppReactNativeBridge
import javax.inject.Singleton


@Module
class DataModule {

    @Provides
    @Singleton
    internal open fun provideExpensesMemoryCache() : ExpensesMemoryCache {
        return ExpensesMemoryCache()
    }

    @Provides
    @Singleton
    internal open fun provideAppReactNativeBridge(reactNativeHost: ReactNativeHost) : AppReactNativeBridge {
        return AppReactNativeBridge(reactNativeHost)
    }
}