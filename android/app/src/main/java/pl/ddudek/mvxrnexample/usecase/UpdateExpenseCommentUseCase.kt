package pl.ddudek.mvxrnexample.usecase

import io.reactivex.Single
import pl.ddudek.mvxrnexample.model.Expense
import pl.ddudek.mvxrnexample.networking.ExpensesApi
import pl.ddudek.mvxrnexample.networking.schema.UpdateExpenseSchema
import java.util.concurrent.TimeUnit

class UpdateExpenseCommentUseCase(val api: ExpensesApi) {
    fun run(expenseId: String, comment: String) : Single<Expense> {
        return api.updateExpense(expenseId, UpdateExpenseSchema(comment))
                .delay(1, TimeUnit.SECONDS)
    }
}