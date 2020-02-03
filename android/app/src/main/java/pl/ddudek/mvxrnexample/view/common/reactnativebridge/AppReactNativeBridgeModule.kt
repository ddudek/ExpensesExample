package pl.ddudek.mvxrnexample.view.common.reactnativebridge

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.ReadableMap

class AppReactNativeBridgeModule(context: ReactApplicationContext, bridge: AppReactNativeBridge) : ReactContextBaseJavaModule(context) {
    private val bridge: AppReactNativeBridge = bridge

    override fun getName(): String {
        return "NativeCallbacksBridge"
    }

    @ReactMethod
    fun onExpenseItemClicked(args: ReadableMap) {
        bridge.listeners.forEach { it.onExpenseItemClicked(args) }
    }

    @ReactMethod
    fun onFilterSelected(index: Int) {
        bridge.listeners.forEach { it.onFilterSelected(index) }
    }

    @ReactMethod
    fun onViewReady() {
        bridge.listeners.forEach { it.onViewReady() }
    }
}