package pl.ddudek.mvxrnexample.view.expenselist

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import pl.ddudek.mvxrnexample.model.Expense
import pl.ddudek.mvxrnexample.view.common.ObservableBaseView

interface ExpensesListView : ObservableBaseView<ExpensesListView.ViewListener> {

    fun onCreated(initialState: ViewState)
    fun applyViewState(state: ViewState)
    fun getState():ViewState

    @Parcelize
    data class ViewState (
            val selectedFilter: Int,
            val expenses: List<Expense>,
            val error: String?,
            val loading: Boolean
    ): Parcelable

    interface ViewListener {
        fun onExpenseItemClicked(expense: Expense)
        fun onFilterSelected(index: Int)
    }
}