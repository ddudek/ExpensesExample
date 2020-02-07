package pl.ddudek.mvxrnexample.view.expensedetails

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection
import pl.ddudek.mvxrnexample.MainApplication
import pl.ddudek.mvxrnexample.data.cache.ExpensesMemoryCache
import pl.ddudek.mvxrnexample.model.Expense
import pl.ddudek.mvxrnexample.networking.ExpensesApi
import pl.ddudek.mvxrnexample.usecase.AddExpenseReceiptUseCase
import pl.ddudek.mvxrnexample.usecase.UpdateExpenseCommentUseCase
import pl.ddudek.mvxrnexample.view.Navigator
import pl.ddudek.mvxrnexample.view.utils.TakePhotoUtil
import pl.ddudek.mvxrnexample.view.utils.TakePhotoUtilImpl
import javax.inject.Inject

class ExpenseDetailsActivity : AppCompatActivity(), TakePhotoUtil.Listener {

    @Inject
    lateinit var cache: ExpensesMemoryCache
    @Inject
    lateinit var api: ExpensesApi

    @Inject
    lateinit var takePhotoUtil: TakePhotoUtil

    @Inject
    lateinit var presenter: ExpenseDetailsPresenter

    @Inject
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
        AndroidInjection.inject(this)
        takePhotoUtil.listener = this
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

    override fun onPhotoReady(fullPath: String) {
        presenter.onPhotoReady(fullPath)
    }
}
