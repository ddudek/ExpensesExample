package pl.ddudek.mvxrnexample.view.common.reactnativebridge

import android.os.Bundle
import com.facebook.react.ReactNativeHost
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter

class AppReactNativeBridge(
        private val reactNativeHost: ReactNativeHost
) {

    val listeners = mutableSetOf<NativeCallbacksBridgeListener>()

    fun registerListener(listener: NativeCallbacksBridgeListener) {
        listeners.add(listener)
    }

    fun unregisterListener(listener: NativeCallbacksBridgeListener) {
        listeners.remove(listener)
    }

    fun sendState(bundleState: Bundle) {
        val reactContext: ReactContext
        try {
            reactContext = reactNativeHost.reactInstanceManager.currentReactContext!!
            val writableMap = Arguments.fromBundle(bundleState)
            reactContext
                    .getJSModule(RCTDeviceEventEmitter::class.java)
                    .emit("stateChanged", writableMap)
        } catch (e: RuntimeException) {
            e.printStackTrace()
        }
    }

    interface NativeCallbacksBridgeListener {
        fun onExpenseItemClicked(args: ReadableMap)
        fun onFilterSelected(index: Int)
        fun onViewReady()
    }
}