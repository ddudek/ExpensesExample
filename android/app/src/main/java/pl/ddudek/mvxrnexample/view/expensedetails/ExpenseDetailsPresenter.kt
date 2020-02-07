package pl.ddudek.mvxrnexample.view.expensedetails

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import pl.ddudek.mvxrnexample.model.Expense
import pl.ddudek.mvxrnexample.usecase.AddExpenseReceiptUseCase
import pl.ddudek.mvxrnexample.usecase.UpdateExpenseCommentUseCase
import pl.ddudek.mvxrnexample.view.Navigator
import pl.ddudek.mvxrnexample.view.utils.TakePhotoUtil
import java.io.File

class ExpenseDetailsPresenter(
        private val updateCommentUseCase: UpdateExpenseCommentUseCase,
        private val addReceiptUseCase: AddExpenseReceiptUseCase,
        private val navigator: Navigator,
        private val takePhotoNavigator: TakePhotoUtil
) : ExpenseDetailsView.ViewListener {

    lateinit var view: ExpenseDetailsView
    private val compositeDisposable = CompositeDisposable()

    private fun createInitialViewState(expense: Expense): ExpenseDetailsView.ViewState {
        return ExpenseDetailsView.ViewState(
                expense,
                isEditing = false,
                savingComment = false,
                savingCommentError = null,
                uploadingReceipt = false,
                uploadingReceiptError = null)
    }

    fun bindView(view: ExpenseDetailsView) {
        this.view = view
    }

    fun onCreate(expense: Expense) {
        view.onCreated(createInitialViewState(expense))
    }

    fun onRecreate(viewState: ExpenseDetailsView.ViewState) {
        view.applyViewState(viewState)
    }

    fun onShow() {
        view.registerListener(this)
    }

    fun onHide() {
        view.unregisterListener(this)
    }

    fun onDestroy() {
        compositeDisposable.dispose()
    }

    fun onPhotoReady(photoPath: String) {
        val expenseId = view.getViewState().expense.id
        val photoFile = File(photoPath)
        setStateUploadingReceipt()
        val disposable = addReceiptUseCase.run(expenseId, photoFile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    setExpenseReceiptUpdated(result)
                }, {
                    setStateUploadingReceiptFailed(it.message)
                })
        compositeDisposable.add(disposable)
    }

    private fun updateViewState(state: ExpenseDetailsView.ViewState) {
        view.applyViewState(state)
    }

    // ---- View listener ----
    override fun onEditClicked() {
        setStateEditingComment()
        view.showEditCommentKeyboard()
    }

    override fun onSaveCommentClicked(comment: String) {
        val expenseId = view.getViewState().expense.id
        setStateSavingCommentInProgress()
        val disposable = updateCommentUseCase.run(expenseId = expenseId, comment = comment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    setStateSavingCommentSuccess(result)
                }, {
                    setStateSavingCommentFailed(it.message)
                })
        compositeDisposable.add(disposable)
    }

    override fun onAddReceiptClicked() {
        takePhotoNavigator.takePhoto()
    }

    override fun onBack() {
        navigator.goBack()
    }
    // ----- End of View Listener ----


    private fun setStateUploadingReceiptFailed(message: String?) {
        updateViewState(view.getViewState().copy(uploadingReceipt = false, uploadingReceiptError = message))
    }

    private fun setExpenseReceiptUpdated(result: Expense) {
        updateViewState(view.getViewState().copy(expense = result, uploadingReceipt = false, uploadingReceiptError = null))
    }

    private fun setStateUploadingReceipt() {
        updateViewState(view.getViewState().copy(uploadingReceipt = true, uploadingReceiptError = null))
    }

    private fun setStateEditingComment() {
        updateViewState(view.getViewState().copy(isEditing = true))
    }

    private fun setStateSavingCommentFailed(message: String?) {
        updateViewState(view.getViewState().copy(isEditing = true, savingComment = false, savingCommentError = message))
    }

    private fun setStateSavingCommentSuccess(result: Expense) {
        updateViewState(view.getViewState().copy(expense = result, isEditing = false, savingComment = false, savingCommentError = null))
    }

    private fun setStateSavingCommentInProgress() {
        updateViewState(view.getViewState().copy(savingComment = true))
    }

    override fun onCancelEditClicked() {
        updateViewState(view.getViewState().copy(isEditing = false, savingComment = false, savingCommentError = null))
    }

}