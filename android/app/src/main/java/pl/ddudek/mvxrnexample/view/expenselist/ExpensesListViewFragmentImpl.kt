package pl.ddudek.mvxrnexample.view.expenselist

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.facebook.react.ReactApplication
import com.facebook.react.ReactFragment
import com.facebook.react.ReactNativeHost
import com.facebook.react.ReactRootView
import pl.ddudek.mvxrnexample.R
import pl.ddudek.mvxrnexample.databinding.ActivityMainBinding
import pl.ddudek.mvxrnexample.databinding.ViewRnFragmentBinding
import pl.ddudek.mvxrnexample.model.Amount
import pl.ddudek.mvxrnexample.model.Expense
import pl.ddudek.mvxrnexample.model.User

class ExpensesListViewFragmentImpl : ExpensesListView {

    lateinit var reactFragment: ReactFragment

    val context: FragmentActivity
    private val viewBinding: ViewRnFragmentBinding

    constructor(context: FragmentActivity, layoutInflater: LayoutInflater) {
        this.context = context
        this.viewBinding = DataBindingUtil.inflate(layoutInflater, R.layout.view_rn_fragment, null, false)
    }

    override fun onCreated(initialState: ExpensesListView.ViewState?) {
        initialState?.let { replaceFragment(it) }
    }

    override fun getRootView(): View {
        return viewBinding.root
    }

    override fun applyViewState(state: ExpensesListView.ViewState) {
        replaceFragment(state)
    }

    private fun replaceFragment(state: ExpensesListView.ViewState) {
        val transaction = context.supportFragmentManager.beginTransaction()
        reactFragment = ReactFragment.Builder().setComponentName("ExpensesList").setLaunchOptions(state?.let { bundleState(it) }).build()
        transaction.replace(R.id.fragment, reactFragment, "REACT_FRAGMENT")
        transaction.commit()
    }

    override fun destroy() {
//        view.unmountReactApplication()
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
