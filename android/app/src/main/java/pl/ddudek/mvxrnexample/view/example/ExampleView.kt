package pl.ddudek.mvxrnexample.view.example

import android.view.View
import pl.ddudek.mvxrnexample.view.common.BaseView

interface ExampleView : BaseView {

    fun onCreated(initialState: ViewState?)
    fun applyViewState(state: ViewState)

    data class ViewState (
            val title: String,
            val subtitle: String,
            val error: String?,
            val loading: Boolean
    )
}