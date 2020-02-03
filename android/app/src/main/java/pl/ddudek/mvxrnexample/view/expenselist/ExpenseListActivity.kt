package pl.ddudek.mvxrnexample.view.expenselist

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler
import pl.ddudek.mvxrnexample.MainApplication
import pl.ddudek.mvxrnexample.data.cache.ExpensesMemoryCache
import pl.ddudek.mvxrnexample.usecase.GetFilteredExpenseListUseCase
import pl.ddudek.mvxrnexample.usecase.ShouldRefreshExpenseListUseCase
import pl.ddudek.mvxrnexample.view.Navigator

class ExpenseListActivity : FragmentActivity(), DefaultHardwareBackBtnHandler {

    private lateinit var view: ExpensesListViewImpl
    private lateinit var presenter: ExpenseListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appComponent = (applicationContext as MainApplication).appComponent
        val useCase = GetFilteredExpenseListUseCase(appComponent.api, appComponent.expensesMemoryCache)
        view = ExpensesListViewImpl(this.supportFragmentManager, appComponent.reactNativeBridge, layoutInflater)
        val navigator = Navigator(this)
        presenter = ExpenseListPresenter(useCase, navigator, ShouldRefreshExpenseListUseCase(appComponent.expensesMemoryCache))
        presenter.bindView(view)
        setContentView(view.getRootView())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable("viewState", view.getState())
        super.onSaveInstanceState(outState)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        presenter.onCreate(savedInstanceState != null)
        savedInstanceState?.let {
            presenter.onRecreate(it.getParcelable("viewState"))
        }
        super.onPostCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        presenter.onShow()
    }

    override fun onStop() {
        presenter.onHide()
        super.onStop()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun invokeDefaultOnBackPressed() {
        onBackPressed()
    }
}
