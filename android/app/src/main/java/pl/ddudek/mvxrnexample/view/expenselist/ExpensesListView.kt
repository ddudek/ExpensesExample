package pl.ddudek.mvxrnexample.view.expenselist

import pl.ddudek.mvxrnexample.model.Expense
import pl.ddudek.mvxrnexample.view.common.BaseView
import pl.ddudek.mvxrnexample.view.common.ObservableBaseView

interface ExpensesListView : ObservableBaseView<ExpensesListView.ViewListener> {

    fun onCreated(initialState: ViewState?)
    fun applyViewState(state: ViewState)

    data class ViewState (
            val expenses: List<Expense>,
            val error: String?,
            val loading: Boolean
    )

    interface ViewListener {
        fun onExpenseItemClicked(expense: Expense)
    }
}