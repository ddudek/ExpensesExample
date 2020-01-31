package pl.ddudek.mvxrnexample.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import pl.ddudek.mvxrnexample.R
import pl.ddudek.mvxrnexample.databinding.ActivityMainBinding

class ExampleViewAndroidImpl (layoutInflater: LayoutInflater, parent: ViewGroup?) : ExampleView {

    private val viewBinding: ActivityMainBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_main, parent, false)

    override fun onCreated(initialState: ExampleView.ViewState?) {
        initialState?.let { applyViewState(initialState) }
    }

    override fun getRootView(): View {
        return viewBinding.root
    }

    override fun applyViewState(state: ExampleView.ViewState) {
        viewBinding.state = state
    }

    override fun destroy() {
        // no op
    }
}
