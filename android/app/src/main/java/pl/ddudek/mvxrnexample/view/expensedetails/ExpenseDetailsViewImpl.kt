package pl.ddudek.mvxrnexample.view.expensedetails

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import pl.ddudek.mvxrnexample.R
import pl.ddudek.mvxrnexample.databinding.ViewExpenseDetailsBinding
import pl.ddudek.mvxrnexample.networking.API_URL
import pl.ddudek.mvxrnexample.view.common.ObservableBaseViewImpl
import java.text.SimpleDateFormat
import java.util.*


class ExpenseDetailsViewImpl(layoutInflater: LayoutInflater, parent: ViewGroup?) :
        ExpenseDetailsView, ObservableBaseViewImpl<ExpenseDetailsView.ViewListener>() {

    private val viewBinding: ViewExpenseDetailsBinding = DataBindingUtil.inflate(layoutInflater, R.layout.view_expense_details, parent, false)

    init {
        setupToolbar()
    }

    override fun onCreated(initialState: ExpenseDetailsView.ViewState) {
        applyViewState(initialState)
        setupViewBindingListener()
    }

    private fun setupToolbar() {
        viewBinding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        viewBinding.toolbar.setNavigationOnClickListener { viewListeners.forEach { it.onBack() } }
    }

    private fun setupViewBindingListener() {
        viewBinding.listener = object : ViewBindingListener {
            override fun onEditClicked(view: View) {
                viewListeners.forEach { it.onEditClicked() }
            }

            override fun onCancelClicked(view: View) {
                viewListeners.forEach { it.onCancelEditClicked() }
            }

            override fun onAddReceiptClicked(view: View) {
                viewListeners.forEach { it.onAddReceiptClicked() }
            }

            override fun onSaveClicked(view: View) {
                val comment = viewBinding.editText.text.toString()
                viewListeners.forEach { it.onSaveCommentClicked(comment) }
            }
        }
    }

    override fun getRootView(): View {
        return viewBinding.root
    }

    override fun getViewState(): ExpenseDetailsView.ViewState {
        return viewBinding.state!!
    }

    override fun applyViewState(state: ExpenseDetailsView.ViewState) {
        val prevState = viewBinding.state
        viewBinding.state = state
        val receiptsChanged = state.expense.receipts != prevState?.expense?.receipts
        if (receiptsChanged) {
            updateReceiptImages(state)
        }
    }

    private fun updateReceiptImages(state: ExpenseDetailsView.ViewState) {
        if (state.expense.receipts.isNotEmpty()) {
            var receipt = state.expense.receipts[0]
            val fullUrl = API_URL + receipt.url
            Glide.with(viewBinding.receipt).load(fullUrl).into(viewBinding.receipt)
        } else {
            viewBinding.receipt.setImageResource(R.drawable.ic_add_black_64dp)
        }
    }

    override fun destroy() {
        // no op
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

    interface ViewBindingListener {
        fun onEditClicked(view: View)
        fun onSaveClicked(view: View)
        fun onCancelClicked(view: View)
        fun onAddReceiptClicked(view: View)
    }
}

@BindingAdapter("bindDate")
fun bindDate(@NonNull textView: TextView?, date: String?) {
    date?.let {
        val serverFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        serverFormat.timeZone = TimeZone.getTimeZone("GMT");
        val presentationFormat = SimpleDateFormat("dd.MM.yyyy H:mma", Locale.getDefault())
        val text = presentationFormat.format(serverFormat.parse(date))
        textView?.text = text
    }
}
