package pl.ddudek.mvxrnexample.view.common

import android.view.View

interface BaseView {
    fun getRootView(): View
    fun destroy()
}