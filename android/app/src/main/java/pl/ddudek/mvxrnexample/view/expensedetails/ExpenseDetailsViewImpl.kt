package pl.ddudek.mvxrnexample.view.expensedetails

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import pl.ddudek.mvxrnexample.R
import pl.ddudek.mvxrnexample.databinding.ViewExpenseDetailsBinding
import pl.ddudek.mvxrnexample.view.common.ObservableBaseViewImpl


class ExpenseDetailsViewImpl(layoutInflater: LayoutInflater, parent: ViewGroup?) :
        ExpenseDetailsView, ObservableBaseViewImpl<ExpenseDetailsView.ViewListener>() {

    private val viewBinding: ViewExpenseDetailsBinding = DataBindingUtil.inflate(layoutInflater, R.layout.view_expense_details, parent, false)

    override fun onCreated(initialState: ExpenseDetailsView.ViewState?) {
        initialState?.let { applyViewState(initialState) }
        viewBinding.listener = object : ViewBindingListener {
            override fun onEditClicked(view: View) {
                for(listener in viewListeners) {
                    listener.onEditClicked()
                }
            }

            override fun onSaveClicked(view: View) {
                for(listener in viewListeners) {
                    val comment = viewBinding.editText.text.toString()
                    listener.onSaveCommentClicked(comment)
                }
            }

            override fun onCancelClicked(view: View) {
                for(listener in viewListeners) {
                    val comment = viewBinding.editText.text.toString()
                    listener.onCancelEditClicked()
                }
            }
        }
    }

    interface ViewBindingListener {
        fun onEditClicked(view: View)
        fun onSaveClicked(view: View)
        fun onCancelClicked(view: View)
    }

    override fun getRootView(): View {
        return viewBinding.root
    }

    override fun applyViewState(state: ExpenseDetailsView.ViewState) {
        viewBinding.state = state
    }

    override fun showEditCommentKeyboard() {
        showKeyboardDelayed()
    }

    private fun showKeyboardDelayed() {
        Handler().postDelayed({
            viewBinding.editText.requestFocus()
            val imm: InputMethodManager? = viewBinding.editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }, 100)
    }

    override fun destroy() {
        // no op
    }
}
