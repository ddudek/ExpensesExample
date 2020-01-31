package pl.ddudek.mvxrnexample.networking

import io.reactivex.Single
import pl.ddudek.mvxrnexample.networking.schema.ExpenseListSchema
import retrofit2.http.GET

interface ExpensesApi {
    @GET("expenses")
    fun getExpensesList(): Single<ExpenseListSchema>
}