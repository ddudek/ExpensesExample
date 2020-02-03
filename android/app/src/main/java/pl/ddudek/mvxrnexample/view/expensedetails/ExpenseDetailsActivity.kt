package pl.ddudek.mvxrnexample.view.expensedetails

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import pl.ddudek.mvxrnexample.MainApplication
import pl.ddudek.mvxrnexample.model.Expense
import pl.ddudek.mvxrnexample.usecase.AddExpenseReceiptUseCase
import pl.ddudek.mvxrnexample.usecase.UpdateExpenseCommentUseCase
import pl.ddudek.mvxrnexample.view.Navigator
import pl.ddudek.mvxrnexample.view.utils.TakePhotoUtil
import pl.ddudek.mvxrnexample.view.utils.TakePhotoUtilImpl

class ExpenseDetailsActivity : AppCompatActivity(), TakePhotoUtil.Listener {

    lateinit var takePhotoUtil: TakePhotoUtilImpl
    lateinit var presenter: ExpenseDetailsPresenter
    lateinit var view: ExpenseDetailsView

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        val expense : Expense = intent.getParcelableExtra("expense")
        presenter.onCreate(expense)

        savedInstanceState?.let {
            presenter.onRecreate(it.getParcelable<ExpenseDetailsView.ViewState>("viewState"))
        }
    }

    override fun onDestroy() {
        presenter.onDestroy()
        takePhotoUtil.listener = null
        super.onDestroy()
    }

    override fun onStart() {
        super.onStart()
        presenter.onShow()
    }

    override fun onStop() {
        super.onStop()
        presenter.onHide()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        takePhotoUtil.onSaveInstanceState(outState)
        outState.putParcelable("viewState", view.getViewState())
        super.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        takePhotoUtil = TakePhotoUtilImpl(this)
        takePhotoUtil.listener = this

        // TODO: Dependency injection
        val appComponent = (applicationContext as MainApplication).appComponent

        view = ExpenseDetailsViewImpl(layoutInflater, null)
        val updateCommentUseCase = UpdateExpenseCommentUseCase(appComponent.api, appComponent.expensesMemoryCache)
        val addReceiptUseCase = AddExpenseReceiptUseCase(appComponent.api, appComponent.expensesMemoryCache)
        val navigator = Navigator(this)

        presenter = ExpenseDetailsPresenter(updateCommentUseCase, addReceiptUseCase, navigator, takePhotoUtil)
        presenter.bindView(view)

        setContentView(view.getRootView())

        savedInstanceState?.let {
            takePhotoUtil.recreateFromSavedInstanceState(it)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        takePhotoUtil.onActivityResult(requestCode, resultCode)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onReceiptPhotoReady(fullPath: String) {
        presenter.onPhotoReady(fullPath)
    }
}
