package pl.ddudek.mvxrnexample.view.common

interface ObservableBaseView<ListenerType> : BaseView {
    fun registerListener(listener: ListenerType)
    fun unregisterListener(listener: ListenerType)
}