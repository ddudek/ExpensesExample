package pl.ddudek.mvxrnexample.view.expenselist.di

import android.app.Activity
import dagger.Module
import dagger.Provides
import pl.ddudek.mvxrnexample.data.cache.ExpensesMemoryCache
import pl.ddudek.mvxrnexample.di.PerActivity
import pl.ddudek.mvxrnexample.networking.ExpensesApi
import pl.ddudek.mvxrnexample.usecase.GetFilteredExpenseListUseCase
import pl.ddudek.mvxrnexample.usecase.ShouldRefreshExpenseListUseCase
import pl.ddudek.mvxrnexample.view.Navigator
import pl.ddudek.mvxrnexample.view.common.reactnativebridge.AppReactNativeBridge
import pl.ddudek.mvxrnexample.view.expenselist.ExpenseListActivity
import pl.ddudek.mvxrnexample.view.expenselist.ExpenseListPresenter
import pl.ddudek.mvxrnexample.view.expenselist.ExpensesListView
import pl.ddudek.mvxrnexample.view.expenselist.ExpensesListViewImpl

@Module
class ExpenseListActivityModule {

    @Provides
    @PerActivity
    internal open fun provideActivity(activity: ExpenseListActivity): Activity {
        return activity
    }

    @Provides
    @PerActivity
    internal open fun provideNavigator(activity: Activity): Navigator {
        return Navigator(activity)
    }

    @Provides
    @PerActivity
    internal open fun provideGetFilteredExpenseListUseCase(api: ExpensesApi, cache: ExpensesMemoryCache): GetFilteredExpenseListUseCase {
        return GetFilteredExpenseListUseCase(api, cache)
    }


    @Provides
    @PerActivity
    internal open fun provideShouldRefreshExpenseListUseCase(cache: ExpensesMemoryCache): ShouldRefreshExpenseListUseCase {
        return ShouldRefreshExpenseListUseCase(cache)
    }

    @Provides
    @PerActivity
    internal open fun providePresenter(
            navigator: Navigator,
            listUseCase: GetFilteredExpenseListUseCase,
            refreshExpenseListUseCase: ShouldRefreshExpenseListUseCase
    ): ExpenseListPresenter {
        return ExpenseListPresenter(listUseCase, navigator, refreshExpenseListUseCase)
    }

    @Provides
    @PerActivity
    internal open fun provideView(
            activity: ExpenseListActivity,
            reactNativeBridge: AppReactNativeBridge
    ): ExpensesListView {
        return ExpensesListViewImpl(activity.supportFragmentManager, reactNativeBridge, activity.layoutInflater)
    }
}