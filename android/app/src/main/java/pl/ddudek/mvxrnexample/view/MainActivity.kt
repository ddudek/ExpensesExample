package pl.ddudek.mvxrnexample.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import pl.ddudek.mvxrnexample.expenselist.GetExpenseListUseCase
import pl.ddudek.mvxrnexample.view.expenselist.ExpensesListView
import pl.ddudek.mvxrnexample.view.expenselist.ExpensesListViewImpl

class MainActivity : AppCompatActivity() {

    lateinit var view: ExpensesListView
    private val useCase = GetExpenseListUseCase()

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view = ExpensesListViewImpl(this)

        setContentView(view.getRootView())
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {

        val initialState = ExpensesListView.ViewState(listOf(), error = null, loading = true)
        view.onCreated(initialState)

        var disposable = useCase.run()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data ->
//                    var expense = data[0]
                    view.applyViewState(ExpensesListView.ViewState(data, error = null, loading = false))
                }, { error ->
                    view.applyViewState(ExpensesListView.ViewState(listOf(), error = "Loading data failed, please try again. \n Details: ${error.message}", loading = false))
                })
        compositeDisposable.add(disposable)

        super.onPostCreate(savedInstanceState)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        view.destroy()
        super.onDestroy()
    }
}
