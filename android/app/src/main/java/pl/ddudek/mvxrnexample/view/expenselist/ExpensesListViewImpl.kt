package pl.ddudek.mvxrnexample.view.expenselist

import android.content.Context
import android.os.Bundle
import android.view.View
import com.facebook.react.ReactApplication
import com.facebook.react.ReactNativeHost
import com.facebook.react.ReactRootView
import pl.ddudek.mvxrnexample.model.Amount
import pl.ddudek.mvxrnexample.model.Expense
import pl.ddudek.mvxrnexample.model.User

class ExpensesListViewImpl(val context : Context) : ExpensesListView {
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

private fun Expense.mapToBundle(): Bundle {
    val bundle = Bundle()
    bundle.putBundle("amount", amount.mapToBundle())
    bundle.putString("category", category)
    bundle.putString("comment", comment)
    bundle.putString("date", date)
    bundle.putString("id", id)
    bundle.putString("merchant", merchant)
    bundle.putSerializable("receipts", receipts.map { it.mapToBundle() }.toTypedArray())
    bundle.putBundle("user", user.mapToBundle())
    return bundle
}

private fun User.mapToBundle(): Bundle? {
    val bundle = Bundle()
    bundle.putString("email", email)
    bundle.putString("first", first)
    bundle.putString("last", last)
    return bundle
}

private fun Amount.mapToBundle(): Bundle {
    val bundle = Bundle()
    bundle.putString("currency", currency)
    bundle.putString("value", value)
    return bundle
}
