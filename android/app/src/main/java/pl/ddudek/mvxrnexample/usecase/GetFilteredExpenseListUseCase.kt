package pl.ddudek.mvxrnexample.usecase

import io.reactivex.Single
import pl.ddudek.mvxrnexample.data.cache.ExpensesMemoryCache
import pl.ddudek.mvxrnexample.model.Expense
import pl.ddudek.mvxrnexample.networking.ExpensesApi
import pl.ddudek.mvxrnexample.networking.mapToModel
import pl.ddudek.mvxrnexample.networking.schema.responses.ExpenseSchema
import java.util.*
import java.util.concurrent.TimeUnit

class GetFilteredExpenseListUseCase(val api: ExpensesApi, val cache: ExpensesMemoryCache) {

    fun run(min: Int, max: Int): Single<List<Expense>> {
        val forceReload = false
        return fetchOrCacheExpenses(forceReload)
                .flattenAsObservable { it }
                .map { it.mapToModel() }
                .filter { filterExpense(it, min, max) }
                .toList()
    }

    private fun fetchOrCacheExpenses(forceReload: Boolean): Single<List<ExpenseSchema>> {
        val cacheInvalidateTime = 5L * 60L * 1000L // 5 mins
        val cacheTooOld = cache.lastFetchTimestamp < Date().time - cacheInvalidateTime
        val cacheEmpty = cache.lastFetchTimestamp == 0L
        if (cacheEmpty || forceReload || cacheTooOld) {
            return api.getExpensesList()
                    .delay(1, TimeUnit.SECONDS)
                    .map {
                        cache.expenses = it.expenses
                        cache.lastFetchTimestamp = Date().time
                        cache.shouldRefreshAll = false
                        return@map it.expenses
                    }
        } else {
            val expenses = cache.expenses
            return Single.just(expenses)
        }
    }

    private fun filterExpense(it: Expense, min: Int, max: Int): Boolean {
        if (min > 0 && it.amount.value.toDouble() < min) {
            return false
        }
        if (max > 0 && it.amount.value.toDouble() > max) {
            return false
        }
        return true
    }

    companion object {
        const val NO_FILTER = -1
    }
}