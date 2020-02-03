package pl.ddudek.mvxrnexample.usecase

import io.reactivex.Single
import pl.ddudek.mvxrnexample.data.cache.ExpensesMemoryCache
import pl.ddudek.mvxrnexample.model.Expense
import pl.ddudek.mvxrnexample.networking.ExpensesApi
import pl.ddudek.mvxrnexample.networking.schema.params.UpdateExpenseSchema
import java.util.concurrent.TimeUnit

class UpdateExpenseCommentUseCase(val api: ExpensesApi, val cache: ExpensesMemoryCache) {
    fun run(expenseId: String, comment: String) : Single<Expense> {
        return api.updateExpense(expenseId, UpdateExpenseSchema(comment))
                .doOnSuccess { cache.invalidate() }
                .delay(1, TimeUnit.SECONDS)
    }
}