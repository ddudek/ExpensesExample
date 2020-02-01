package pl.ddudek.mvxrnexample.view.common.reactnativebridge

import com.facebook.react.bridge.ReadableMap

class AppReactNativeBridge {
    val listeners = mutableSetOf<NativeCallbacksBridgeListener>()

    fun registerListener(listener: NativeCallbacksBridgeListener) {
        listeners.add(listener)
    }

    fun unregisterListener(listener: NativeCallbacksBridgeListener) {
        listeners.remove(listener)
    }

    interface NativeCallbacksBridgeListener {
        fun onExpenseItemClicked(args: ReadableMap)
    }
}