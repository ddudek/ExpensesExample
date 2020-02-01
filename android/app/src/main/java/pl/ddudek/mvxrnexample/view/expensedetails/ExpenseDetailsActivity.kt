package pl.ddudek.mvxrnexample.view.expensedetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import pl.ddudek.mvxrnexample.MainApplication
import pl.ddudek.mvxrnexample.model.Expense
import pl.ddudek.mvxrnexample.usecase.AddExpenseReceiptUseCase
import pl.ddudek.mvxrnexample.usecase.UpdateExpenseCommentUseCase

class ExpenseDetailsActivity : AppCompatActivity() {

    lateinit var view: ExpenseDetailsView
    lateinit var updateCommentUseCase: UpdateExpenseCommentUseCase
    lateinit var addReceiptUseCase: AddExpenseReceiptUseCase
    lateinit var expense: Expense

    lateinit var currentState : ExpenseDetailsView.ViewState

    val listener = object : ExpenseDetailsView.ViewListener {
        override fun onEditClicked() {
            updateViewState(currentState.copy(isEditing = true))
            view.showEditCommentKeyboard()
        }

        override fun onSaveCommentClicked(comment: String) {
            updateViewState(currentState.copy(loading = true))
            val disposable = updateCommentUseCase.run(expenseId = expense.id, comment = comment)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ result ->
                        updateViewState(currentState.copy(expense = result, isEditing = false, loading = false, error = null))
                    }, {
                        updateViewState(currentState.copy(isEditing = true, loading = false, error = it.message))
                    })
        }

        override fun onCancelEditClicked() {
            updateViewState(currentState.copy(isEditing = false, loading = false, error = null))
        }
    }

    private fun updateViewState(state: ExpenseDetailsView.ViewState) {
        currentState = state
        view.applyViewState(state)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appComponent = (applicationContext as MainApplication).appComponent
        view = ExpenseDetailsViewImpl(layoutInflater, null)
        updateCommentUseCase = UpdateExpenseCommentUseCase(appComponent.api)
        addReceiptUseCase = AddExpenseReceiptUseCase(appComponent.api)
        expense = intent.getParcelableExtra("expense")
        setContentView(view.getRootView())
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        currentState = ExpenseDetailsView.ViewState(expense, isEditing = false, loading = false, error = null)
        view.onCreated(currentState)
    }

    override fun onStart() {
        super.onStart()
        view.registerListener(listener)
    }

    override fun onStop() {
        super.onStop()
        view.unregisterListener(listener)
    }
}
