package pl.ddudek.mvxrnexample.usecase

import com.google.gson.Gson
import io.reactivex.Single
import pl.ddudek.mvxrnexample.model.Expense
import pl.ddudek.mvxrnexample.networking.ExpensesApi
import pl.ddudek.mvxrnexample.networking.mapToModel
import pl.ddudek.mvxrnexample.networking.schema.ExpenseSchema
import java.util.concurrent.TimeUnit


class GetExpenseDetailsUseCase(val api: ExpensesApi) {
    fun run() : Single<List<Expense>> {
        return api.getExpensesList()
                .map { it.expenses }
                .flattenAsObservable { it}
                .map { it.mapToModel() }
                .toList()
                .delay(5, TimeUnit.SECONDS)
    }

    private fun createSampleExpense(): List<ExpenseSchema> {
        return listOf(
                Gson().fromJson("{\"id\":\"5b996064dfd5b783915112f5\",\"amount\":{\"value\":\"1854.99\",\"currency\":\"EUR\"},\"date\":\"2018-09-10T02:11:29.184Z\",\"merchant\":\"KAGE\",\"receipts\":[],\"comment\":\"\",\"category\":\"\",\"user\":{\"first\":\"Vickie\",\"last\":\"Lee\",\"email\":\"vickie.lee@pleo.io\"}}", ExpenseSchema::class.java),
                Gson().fromJson("{\"id\":\"5b996064ba218563e3ed5935\",\"amount\":{\"value\":\"3383.76\",\"currency\":\"DKK\"},\"date\":\"2018-03-23T08:31:02.663Z\",\"merchant\":\"ELEMANTRA\",\"receipts\":[],\"comment\":\"\",\"category\":\"\",\"user\":{\"first\":\"Frances\",\"last\":\"Atkins\",\"email\":\"frances.atkins@pleo.io\"}}", ExpenseSchema::class.java),
                Gson().fromJson("{\"id\":\"5b99606492951fe4481be7c6\",\"amount\":{\"value\":\"2069.83\",\"currency\":\"EUR\"},\"date\":\"2018-02-22T16:25:40.540Z\",\"merchant\":\"EMERGENT\",\"receipts\":[],\"comment\":\"\",\"category\":\"\",\"user\":{\"first\":\"Christensen\",\"last\":\"Trevino\",\"email\":\"christensen.trevino@pleo.io\"}}", ExpenseSchema::class.java)
        )
    }
}