package pl.ddudek.mvxrnexample.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.facebook.react.ReactApplication
import com.facebook.react.ReactNativeHost
import com.facebook.react.ReactRootView

class ExampleViewReactNativeImpl(val context: Context, parent: ViewGroup?) : ExampleView {

    private val view: ReactRootView = ReactRootView(context)

    override fun onCreated(initialState: ExampleView.ViewState?) {
        view.startReactApplication(getReactNativeHost()?.reactInstanceManager, "MainMvxView", initialState?.let { bundleState(it) })
    }

    override fun getRootView(): View {
        return view
    }

    override fun applyViewState(state: ExampleView.ViewState) {
        view.appProperties = bundleState(state)
    }

    override fun destroy() {
        view.unmountReactApplication()
    }

    private fun getReactNativeHost(): ReactNativeHost? {
        return (context.applicationContext as ReactApplication).reactNativeHost
    }

    private fun bundleState(state: ExampleView.ViewState): Bundle {
        return Bundle().apply {
            putString("title", state.title)
            putString("subtitle", state.subtitle)
            putBoolean("loading", state.loading)
            putString("error", state.error)
        }
    }
}
