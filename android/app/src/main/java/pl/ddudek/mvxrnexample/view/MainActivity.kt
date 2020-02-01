package pl.ddudek.mvxrnexample.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import pl.ddudek.mvxrnexample.MainApplication
import pl.ddudek.mvxrnexample.usecase.GetExpenseListUseCase
import pl.ddudek.mvxrnexample.view.expensedetails.ExpenseDetailsActivity
import pl.ddudek.mvxrnexample.view.expenselist.ExpensesListView
import pl.ddudek.mvxrnexample.view.expenselist.ExpensesListViewFragmentImpl

class MainActivity : FragmentActivity(), DefaultHardwareBackBtnHandler {

    private lateinit var viewListener: ExpensesListView.ViewListener
    lateinit var view: ExpensesListView
    lateinit var useCase: GetExpenseListUseCase

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appComponent = (applicationContext as MainApplication).appComponent
        useCase = GetExpenseListUseCase(appComponent.api)
        view = ExpensesListViewFragmentImpl(this.supportFragmentManager, appComponent.bridgeCallbackListeners, layoutInflater)
        viewListener = object : ExpensesListView.ViewListener {
            override fun onExpenseItemClicked(id: String) {
                val intent = Intent(this@MainActivity, ExpenseDetailsActivity::class.java)
                intent.putExtra("id", id)
                startActivity(intent)
            }
        }

        setContentView(view.getRootView())
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {

        val initialState = ExpensesListView.ViewState(listOf(), error = null, loading = true)
        view.onCreated(initialState)

        var disposable = useCase.run()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data ->
                    view.applyViewState(ExpensesListView.ViewState(data, error = null, loading = false))
                }, { error ->
                    view.applyViewState(ExpensesListView.ViewState(listOf(), error = "Loading data failed, please try again. \n Details: ${error.message}", loading = false))
                })
        compositeDisposable.add(disposable)

        super.onPostCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        view.registerListener(viewListener)
    }

    override fun onStop() {
        view.registerListener(viewListener)
        super.onStop()
    }
    override fun onDestroy() {
        compositeDisposable.dispose()
        view.destroy()
        super.onDestroy()
    }

    override fun invokeDefaultOnBackPressed() {
        onBackPressed()
    }
}
