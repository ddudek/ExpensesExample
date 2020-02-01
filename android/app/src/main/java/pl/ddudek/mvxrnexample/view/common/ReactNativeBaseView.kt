package pl.ddudek.mvxrnexample.view.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.facebook.react.ReactFragment
import pl.ddudek.mvxrnexample.R
import pl.ddudek.mvxrnexample.databinding.ViewRnFragmentBinding
import pl.ddudek.mvxrnexample.view.common.reactnativebridge.NativeCallbacksBridgeListeners


abstract class ReactNativeBaseView<ViewListenerType, ViewStateType>(
        private val supportFragmentManager: FragmentManager,
        layoutInflater: LayoutInflater,
        listeners: NativeCallbacksBridgeListeners) :
        ObservableBaseViewImpl<ViewListenerType>() {

    protected var bridgeCallbackListeners: NativeCallbacksBridgeListeners = listeners
    private val viewBinding: ViewRnFragmentBinding = DataBindingUtil.inflate(layoutInflater, R.layout.view_rn_fragment, null, false)
    private lateinit var reactFragment: ReactFragment

    open fun onCreated(initialState: ViewStateType?) {
        initialState?.let { replaceFragment(it) }
    }

    override fun getRootView(): View {
        return viewBinding.root
    }

    fun applyViewState(state: ViewStateType) {
        replaceFragment(state)
    }

    private fun replaceFragment(state: ViewStateType) {
        val transaction = supportFragmentManager.beginTransaction()
        reactFragment = ReactFragment.Builder()
                .setComponentName(getRNModuleName())
                .setLaunchOptions(state?.let { bundleState(it) }).build()
        transaction.replace(R.id.fragment, reactFragment, "REACT_FRAGMENT")
        transaction.commit()
    }

    abstract fun bundleState(viewState: ViewStateType): Bundle

    abstract fun getRNModuleName(): String
}
