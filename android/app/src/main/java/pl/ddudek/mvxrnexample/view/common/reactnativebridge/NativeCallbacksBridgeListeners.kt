package pl.ddudek.mvxrnexample.view.common.reactnativebridge

class NativeCallbacksBridgeListeners {
    val listeners = mutableSetOf<NativeCallbacksBridgeListener>()

    fun registerListener(listener: NativeCallbacksBridgeListener) {
        listeners.add(listener)
    }

    fun unregisterListener(listener: NativeCallbacksBridgeListener) {
        listeners.remove(listener)
    }

    interface NativeCallbacksBridgeListener {
        fun onExpenseItemClicked(id: String)
    }
}