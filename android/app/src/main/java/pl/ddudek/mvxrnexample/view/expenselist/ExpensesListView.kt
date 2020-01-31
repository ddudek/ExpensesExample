package pl.ddudek.mvxrnexample.view.expenselist

import pl.ddudek.mvxrnexample.model.Expense
import pl.ddudek.mvxrnexample.view.common.BaseView

interface ExpensesListView : BaseView {

    data class ViewState (
            val expenses: List<Expense>,
            val error: String?,
            val loading: Boolean
    )
}