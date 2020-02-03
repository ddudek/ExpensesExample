package pl.ddudek.mvxrnexample.view.expensedetails

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import pl.ddudek.mvxrnexample.model.Expense
import pl.ddudek.mvxrnexample.view.common.ObservableBaseView

interface ExpenseDetailsView : ObservableBaseView<ExpenseDetailsView.ViewListener> {

    fun onCreated(initialState: ViewState)
    fun applyViewState(state: ViewState)
    fun getViewState(): ViewState
    fun showEditCommentKeyboard()

    @Parcelize
    data class ViewState(
            val expense: Expense,
            val isEditing: Boolean,
            val savingComment: Boolean,
            val savingCommentError: String?,
            val uploadingReceipt: Boolean,
            val uploadingReceiptError: String?
    ) : Parcelable

    interface ViewListener {
        fun onEditClicked()
        fun onSaveCommentClicked(comment: String)
        fun onCancelEditClicked()
        fun onAddReceiptClicked()
        fun onBack()
    }
}