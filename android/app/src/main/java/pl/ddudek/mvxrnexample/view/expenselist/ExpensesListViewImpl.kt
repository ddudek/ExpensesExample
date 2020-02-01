package pl.ddudek.mvxrnexample.view.expenselist

import android.content.Context
import android.os.Bundle
import android.view.View
import com.facebook.react.ReactApplication
import com.facebook.react.ReactNativeHost
import com.facebook.react.ReactRootView
import pl.ddudek.mvxrnexample.view.common.ObservableBaseViewImpl
import pl.ddudek.mvxrnexample.view.expenselist.mapper.mapToBundle

class ExpensesListViewImpl(val context : Context) :
        ExpensesListView,
        ObservableBaseViewImpl<ExpensesListView.ViewListener>() {
    private val view: ReactRootView = ReactRootView(context)

    override fun onCreated(initialState: ExpensesListView.ViewState?) {
        view.startReactApplication(getReactNativeHost()?.reactInstanceManager, "ExpensesList", initialState?.let { bundleState(it) })
    }

    override fun getRootView(): View {
        return view
    }

    override fun applyViewState(state: ExpensesListView.ViewState) {
        view.appProperties = bundleState(state)
    }

    override fun destroy() {
        view.unmountReactApplication()
    }

    private fun getReactNativeHost(): ReactNativeHost? {
        return (context.applicationContext as ReactApplication).reactNativeHost
    }

    private fun bundleState(state: ExpensesListView.ViewState): Bundle {
        return Bundle().apply {
            putSerializable("expenses", state.expenses.map { it.mapToBundle() }.toTypedArray())
            putBoolean("loading", state.loading)
            putString("error", state.error)
        }
    }
}

