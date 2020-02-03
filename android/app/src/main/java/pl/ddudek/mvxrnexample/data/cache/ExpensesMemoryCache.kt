package pl.ddudek.mvxrnexample.data.cache

import pl.ddudek.mvxrnexample.networking.schema.responses.ExpenseSchema

class ExpensesMemoryCache {
    var expenses: List<ExpenseSchema> = listOf()
    var lastFetchTimestamp = 0L
    var shouldRefreshAll = false

    fun invalidate() {
        lastFetchTimestamp = 0L
        expenses = listOf()
        shouldRefreshAll = true
    }
}