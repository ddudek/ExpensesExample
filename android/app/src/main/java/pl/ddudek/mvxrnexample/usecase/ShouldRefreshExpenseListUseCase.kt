package pl.ddudek.mvxrnexample.usecase

import pl.ddudek.mvxrnexample.data.cache.ExpensesMemoryCache

class ShouldRefreshExpenseListUseCase(val cache: ExpensesMemoryCache) {
    fun run(): Boolean {
        return cache.shouldRefreshAll
    }
}