package pl.ddudek.mvxrnexample.view.expenselist

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler
import dagger.android.AndroidInjection
import pl.ddudek.mvxrnexample.MainApplication
import pl.ddudek.mvxrnexample.data.cache.ExpensesMemoryCache
import pl.ddudek.mvxrnexample.networking.ExpensesApi
import pl.ddudek.mvxrnexample.usecase.GetFilteredExpenseListUseCase
import pl.ddudek.mvxrnexample.usecase.ShouldRefreshExpenseListUseCase
import pl.ddudek.mvxrnexample.view.Navigator
import pl.ddudek.mvxrnexample.view.common.reactnativebridge.AppReactNativeBridge
import javax.inject.Inject

class ExpenseListActivity : FragmentActivity(), DefaultHardwareBackBtnHandler {

    @Inject
    lateinit var view: ExpensesListView

    @Inject
    lateinit var presenter: ExpenseListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidInjection.inject(this)

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
