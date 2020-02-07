package pl.ddudek.mvxrnexample.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.ddudek.mvxrnexample.view.expensedetails.ExpenseDetailsActivity
import pl.ddudek.mvxrnexample.view.expensedetails.di.ExpenseDetailsModule
import pl.ddudek.mvxrnexample.view.expenselist.ExpenseListActivity
import pl.ddudek.mvxrnexample.view.expenselist.di.ExpenseListActivityModule
import javax.inject.Scope

@Module
internal abstract class ActivitiesModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [ExpenseDetailsModule::class])
    abstract fun contributeExpenseDetailsActivity(): ExpenseDetailsActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [ExpenseListActivityModule::class])
    abstract fun contributeExpenseListActivity(): ExpenseListActivity
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PerActivity