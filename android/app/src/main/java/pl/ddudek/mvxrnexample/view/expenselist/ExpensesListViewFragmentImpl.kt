package pl.ddudek.mvxrnexample.view.expenselist

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.FragmentManager
import com.facebook.react.bridge.ReadableMap
import pl.ddudek.mvxrnexample.view.common.ReactNativeBaseView
import pl.ddudek.mvxrnexample.view.common.reactnativebridge.AppReactNativeBridge
import pl.ddudek.mvxrnexample.view.expenselist.mapper.mapToBundle
import pl.ddudek.mvxrnexample.view.expenselist.mapper.mapToExpense

class ExpensesListViewFragmentImpl(fragmentManager: FragmentManager, bridge: AppReactNativeBridge, layoutInflater: LayoutInflater) :
        ReactNativeBaseView<ExpensesListView.ViewListener, ExpensesListView.ViewState>(fragmentManager, layoutInflater, bridge),
        ExpensesListView {

    private val rnBridgeListener = object : AppReactNativeBridge.NativeCallbacksBridgeListener {
        override fun onExpenseItemClicked(args: ReadableMap) {
            val expense = args.mapToExpense()
            for (listener in viewListeners) {
                listener.onExpenseItemClicked(expense)
            }
        }
    }

    override fun getRNModuleName() = "ExpensesList"

    override fun onCreated(initialState: ExpensesListView.ViewState?) {
        super.onCreated(initialState)
        bridge.registerListener(rnBridgeListener)
    }

    override fun destroy() {
        bridge.unregisterListener(rnBridgeListener)
    }

    override fun bundleState(viewState: ExpensesListView.ViewState): Bundle {
        return Bundle().apply {
            putSerializable("expenses", viewState.expenses.map { it.mapToBundle() }.toTypedArray())
            putBoolean("loading", viewState.loading)
            putString("error", viewState.error)
        }
    }
}

