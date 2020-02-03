package pl.ddudek.mvxrnexample.view.expenselist

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import pl.ddudek.mvxrnexample.model.Expense
import pl.ddudek.mvxrnexample.usecase.GetFilteredExpenseListUseCase
import pl.ddudek.mvxrnexample.usecase.ShouldRefreshExpenseListUseCase
import pl.ddudek.mvxrnexample.view.Navigator

class ExpenseListPresenter(
        private val useCase: GetFilteredExpenseListUseCase,
        private val navigator: Navigator,
        private val shouldRefreshExpensesUseCase: ShouldRefreshExpenseListUseCase
) : ExpensesListView.ViewListener
{

    lateinit var view: ExpensesListView
    private val compositeDisposable = CompositeDisposable()


    private fun createInitialState(): ExpensesListView.ViewState {
        return ExpensesListView.ViewState(
                selectedFilter = GetFilteredExpenseListUseCase.NO_FILTER,
                expenses = listOf(),
                error = null,
                loading = true)
    }


    fun bindView(view: ExpensesListView) {
        this.view = view
    }

    fun onCreate(recreate: Boolean) {
        val initialState = createInitialState()
        view.onCreated(initialState)

        if(!recreate) {
            loadExpensesFiltered(
                    min = GetFilteredExpenseListUseCase.NO_FILTER,
                    max = GetFilteredExpenseListUseCase.NO_FILTER)
        }
    }

    fun onRecreate(state: ExpensesListView.ViewState?) {
        state?.let { view.applyViewState(state) }
    }

    fun onShow() {
        view.registerListener(this)
        val shouldRefreshList = shouldRefreshExpensesUseCase.run()
        if (shouldRefreshList) {
            reloadExpensesWithFilter(view.getState().selectedFilter)
        }
    }

    fun onHide() {
        view.unregisterListener(this)
    }

    fun onDestroy() {
        compositeDisposable.dispose()
        view.destroy()
    }

    override fun onExpenseItemClicked(expense: Expense) {
        navigator.toExpenseDetails(expense)
    }

    override fun onFilterSelected(index: Int) {
        reloadExpensesWithFilter(index)
    }

    private fun reloadExpensesWithFilter(index: Int) {
        var (min, max) = selectedFilterToMinMax(index)
        setExpensesLoading(index)
        loadExpensesFiltered(min, max)
    }

    private fun loadExpensesFiltered(min: Int, max: Int) {
        var disposable = useCase.run(min, max)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data ->
                    setExpensesLoaded(data)
                }, { error ->
                    setExpensesLoadFailure(error)
                })
        compositeDisposable.add(disposable)
    }

    private fun setExpensesLoading(index: Int) {
        view.applyViewState(view.getState().copy(
                selectedFilter = index,
                error = null,
                loading = true
        ))
    }

    private fun setExpensesLoaded(data: List<Expense>) {
        view.applyViewState(view.getState().copy(
                expenses = data,
                error = null,
                loading = false
        ))
    }

    private fun setExpensesLoadFailure(error: Throwable) {
        view.applyViewState(view.getState().copy(
                expenses = listOf(),
                error = "Loading expenses failed, please try again.\n Details: ${error.message}",
                loading = false
        ))
    }

    private fun selectedFilterToMinMax(index: Int): Pair<Int, Int> {
        var min = GetFilteredExpenseListUseCase.NO_FILTER
        var max = GetFilteredExpenseListUseCase.NO_FILTER
        // would be nice to make this code a bit better ;-)
        when (index) {
            0 -> {
                max = 1000
            }
            1 -> {
                min = 1000
                max = 10000
            }
            2 -> {
                min = 10000
            }
        }
        return Pair(min, max)
    }

}