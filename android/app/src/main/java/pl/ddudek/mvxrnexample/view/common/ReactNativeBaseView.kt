package pl.ddudek.mvxrnexample.view.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.facebook.react.ReactFragment
import pl.ddudek.mvxrnexample.R
import pl.ddudek.mvxrnexample.databinding.ViewRnFragmentBinding
import pl.ddudek.mvxrnexample.view.common.reactnativebridge.AppReactNativeBridge


abstract class ReactNativeBaseView<ViewListenerType: Any, ViewStateType: Any>(
        private val supportFragmentManager: FragmentManager,
        layoutInflater: LayoutInflater,
        bridge: AppReactNativeBridge) :
        ObservableBaseViewImpl<ViewListenerType>() {

    private val fragmentTag = "REACT_FRAGMENT"
    protected var bridge: AppReactNativeBridge = bridge
    private val viewBinding: ViewRnFragmentBinding = DataBindingUtil.inflate(layoutInflater, R.layout.view_rn_fragment, null, false)
    private lateinit var reactFragment: ReactFragment

    private lateinit var currentState : ViewStateType

    open fun onCreated(initialState: ViewStateType) {
        currentState = initialState
        replaceFragment(initialState)
    }

    override fun getRootView(): View {
        return viewBinding.root
    }

    fun applyViewState(state: ViewStateType) {
        currentState = state
        bridge.sendState(bundleState(state))
    }

    fun getState() : ViewStateType {
        return currentState
    }

    private fun replaceFragment(state: ViewStateType) {
        val launchOptions = Bundle()
        launchOptions.putBundle("initialState", bundleState(state))

        val transaction = supportFragmentManager.beginTransaction()
        reactFragment = ReactFragment.Builder()
                .setComponentName(getRNModuleName())
                .setLaunchOptions(launchOptions).build()
        transaction.replace(R.id.fragment, reactFragment, fragmentTag)
        transaction.commit()
    }

    abstract fun bundleState(viewState: ViewStateType): Bundle

    abstract fun getRNModuleName(): String
}
