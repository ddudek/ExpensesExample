package pl.ddudek.mvxrnexample.view.expensedetails.di

import dagger.Module
import dagger.Provides
import pl.ddudek.mvxrnexample.data.cache.ExpensesMemoryCache
import pl.ddudek.mvxrnexample.di.PerActivity
import pl.ddudek.mvxrnexample.networking.ExpensesApi
import pl.ddudek.mvxrnexample.usecase.AddExpenseReceiptUseCase
import pl.ddudek.mvxrnexample.usecase.UpdateExpenseCommentUseCase
import pl.ddudek.mvxrnexample.view.Navigator
import pl.ddudek.mvxrnexample.view.expensedetails.ExpenseDetailsActivity
import pl.ddudek.mvxrnexample.view.expensedetails.ExpenseDetailsPresenter
import pl.ddudek.mvxrnexample.view.expensedetails.ExpenseDetailsView
import pl.ddudek.mvxrnexample.view.expensedetails.ExpenseDetailsViewImpl
import pl.ddudek.mvxrnexample.view.utils.TakePhotoUtil
import pl.ddudek.mvxrnexample.view.utils.TakePhotoUtilImpl

@Module
class ExpenseDetailsModule {

    @PerActivity
    @Provides
    internal open fun provideNavigator(activity: ExpenseDetailsActivity): Navigator {
        return Navigator(activity)
    }

    @PerActivity
    @Provides
    internal open fun provideUpdateExpenseCommentUseCase(api: ExpensesApi, cache: ExpensesMemoryCache): UpdateExpenseCommentUseCase {
        return UpdateExpenseCommentUseCase(api, cache)
    }

    @PerActivity
    @Provides
    internal open fun provideAddExpenseReceiptUseCase(api: ExpensesApi, cache: ExpensesMemoryCache): AddExpenseReceiptUseCase {
        return AddExpenseReceiptUseCase(api, cache)
    }

    @PerActivity
    @Provides
    internal open fun provideTakePhotoUtil(activity: ExpenseDetailsActivity): TakePhotoUtil {
        return TakePhotoUtilImpl(activity)
    }

    @PerActivity
    @Provides
    internal open fun provideExpenseDetailsView(activity: ExpenseDetailsActivity): ExpenseDetailsView {
        return ExpenseDetailsViewImpl(activity.layoutInflater, null)
    }

    @PerActivity
    @Provides
    internal open fun provideExpenseDetailsPresenter(
            updateCommentUseCase: UpdateExpenseCommentUseCase,
            addReceiptUseCase: AddExpenseReceiptUseCase,
            navigator: Navigator,
            takePhotoNavigator: TakePhotoUtil): ExpenseDetailsPresenter {
        return ExpenseDetailsPresenter(updateCommentUseCase, addReceiptUseCase, navigator, takePhotoNavigator)
    }


}