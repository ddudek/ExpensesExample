package pl.ddudek.mvxrnexample.view.expensedetails

import pl.ddudek.mvxrnexample.model.Expense
import pl.ddudek.mvxrnexample.view.common.ObservableBaseView

interface ExpenseDetailsView : ObservableBaseView<ExpenseDetailsView.ViewListener> {

    fun onCreated(initialState: ViewState?)
    fun applyViewState(state: ViewState)
    fun showEditCommentKeyboard()

    data class ViewState(
            val expense: Expense,
            val isEditing: Boolean,
            val loading: Boolean,
            val error: String?
    )

    interface ViewListener {
        fun onEditClicked()
        fun onSaveCommentClicked(comment: String)
        fun onCancelEditClicked()
    }
}