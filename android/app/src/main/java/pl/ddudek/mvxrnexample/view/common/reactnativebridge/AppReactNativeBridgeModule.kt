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
        for (listener in bridge.listeners) {
            listener.onExpenseItemClicked(args)
        }
    }
}