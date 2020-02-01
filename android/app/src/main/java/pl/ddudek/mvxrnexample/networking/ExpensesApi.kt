package pl.ddudek.mvxrnexample.networking

import io.reactivex.Single
import okhttp3.MultipartBody
import pl.ddudek.mvxrnexample.model.Expense
import pl.ddudek.mvxrnexample.networking.schema.ExpenseListSchema
import pl.ddudek.mvxrnexample.networking.schema.UpdateExpenseSchema
import retrofit2.http.*

interface ExpensesApi {
    @GET("expenses")
    fun getExpensesList(): Single<ExpenseListSchema>

    @GET("expenses")
    fun getExpensesList(@Query("limit") limit:Int, @Query("offset") offset:Int): Single<ExpenseListSchema>

    @POST("expenses/{id}")
    fun updateExpense(@Path("id") id:String, @Body updateExpenseBody: UpdateExpenseSchema): Single<Expense>

    @POST("expenses/{id}/receipts")
    fun addReceipt(@Path("id") id:String, @Part photo: MultipartBody.Part): Single<Expense>
}