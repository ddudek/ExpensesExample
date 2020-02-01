package pl.ddudek.mvxrnexample.view.common

abstract class ObservableBaseViewImpl<ListenerType> : ObservableBaseView<ListenerType> {
    protected val viewListeners = mutableSetOf<ListenerType>()

    override fun registerListener(listener: ListenerType) {
        viewListeners.add(listener)
    }

    override fun unregisterListener(listener: ListenerType) {
        viewListeners.remove(listener)
    }
}