package pl.ddudek.mvxrnexample.view

import android.app.Activity
import android.content.Intent
import pl.ddudek.mvxrnexample.model.Expense
import pl.ddudek.mvxrnexample.view.expensedetails.ExpenseDetailsActivity

class Navigator(val activity: Activity) {

    fun goBack() {
        activity.finish()
    }

    fun toExpenseDetails(expense: Expense) {
        val intent = Intent(activity, ExpenseDetailsActivity::class.java)
        intent.putExtra("expense", expense)
        activity.startActivity(intent)
    }
}