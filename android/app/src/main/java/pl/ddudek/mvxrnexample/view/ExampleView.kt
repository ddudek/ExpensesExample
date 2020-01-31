package pl.ddudek.mvxrnexample.view

import android.view.View

interface ExampleView {
    fun onCreated(initialState: ViewState?)
    fun getRootView(): View
    fun applyViewState(state: ViewState)
    fun destroy()

    data class ViewState (
            val title: String,
            val subtitle: String,
            val error: String?,
            val loading: Boolean
    )
}