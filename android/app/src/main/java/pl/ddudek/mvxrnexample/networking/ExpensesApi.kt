package pl.ddudek.mvxrnexample.networking

import io.reactivex.Single
import pl.ddudek.mvxrnexample.model.Expense
import pl.ddudek.mvxrnexample.networking.schema.ExpenseListSchema
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ExpensesApi {
    @GET("expenses")
    fun getExpensesList(): Single<ExpenseListSchema>

    @GET("expenses")
    fun getExpensesList(@Query("limit") limit:Int, @Query("offset") offset:Int): Single<ExpenseListSchema>

    @POST("expense/{id}")
    fun updateExpense(@Query("id") limit:Int, @Query("offset") offset:Int): Single<ExpenseListSchema>
}