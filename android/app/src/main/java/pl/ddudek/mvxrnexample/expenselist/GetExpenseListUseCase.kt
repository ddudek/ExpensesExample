package pl.ddudek.mvxrnexample.expenselist

import com.google.gson.Gson
import io.reactivex.Single
import pl.ddudek.mvxrnexample.model.Expense
import pl.ddudek.mvxrnexample.networking.mapToModel
import pl.ddudek.mvxrnexample.networking.schema.ExpenseSchema
import java.util.concurrent.TimeUnit

class GetExpenseListUseCase {
    fun run() : Single<List<Expense>> {
        return Single
                .just(listOf(createSampleExpense()))
                .flattenAsObservable { it}
                .map { it.mapToModel() }
                .toList()
                .delay(5, TimeUnit.SECONDS)
    }

    private fun createSampleExpense(): ExpenseSchema {
        return Gson().fromJson("{\"id\":\"5b996064dfd5b783915112f5\",\"amount\":{\"value\":\"1854.99\",\"currency\":\"EUR\"},\"date\":\"2018-09-10T02:11:29.184Z\",\"merchant\":\"KAGE\",\"receipts\":[],\"comment\":\"\",\"category\":\"\",\"user\":{\"first\":\"Vickie\",\"last\":\"Lee\",\"email\":\"vickie.lee@pleo.io\"}}", ExpenseSchema::class.java)
    }
}