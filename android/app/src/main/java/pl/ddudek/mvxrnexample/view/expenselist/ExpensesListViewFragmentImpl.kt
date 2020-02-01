package pl.ddudek.mvxrnexample.view.expenselist

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.FragmentManager
import pl.ddudek.mvxrnexample.view.common.ReactNativeBaseView
import pl.ddudek.mvxrnexample.view.common.reactnativebridge.NativeCallbacksBridgeListeners
import pl.ddudek.mvxrnexample.view.expenselist.mapper.mapToBundle

class ExpensesListViewFragmentImpl(fragmentManager: FragmentManager, listeners: NativeCallbacksBridgeListeners, layoutInflater: LayoutInflater) :
        ReactNativeBaseView<ExpensesListView.ViewListener, ExpensesListView.ViewState>(fragmentManager, layoutInflater, listeners),
        ExpensesListView {

    private val rnCallbacksListener = object : NativeCallbacksBridgeListeners.NativeCallbacksBridgeListener {
        override fun onExpenseItemClicked(id: String) {
            for (listener in viewListeners) {
                listener.onExpenseItemClicked(id)
            }
        }
    }

    override fun getRNModuleName() = "ExpensesList"

    override fun onCreated(initialState: ExpensesListView.ViewState?) {
        super.onCreated(initialState)
        bridgeCallbackListeners.registerListener(rnCallbacksListener)
    }

    override fun destroy() {
        bridgeCallbackListeners.unregisterListener(rnCallbacksListener)
    }

    override fun bundleState(viewState: ExpensesListView.ViewState): Bundle {
        return Bundle().apply {
            putSerializable("expenses", viewState.expenses.map { it.mapToBundle() }.toTypedArray())
            putBoolean("loading", viewState.loading)
            putString("error", viewState.error)
        }
    }
}

