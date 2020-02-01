package pl.ddudek.mvxrnexample.view.expensedetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_expense_details.*
import pl.ddudek.mvxrnexample.R

class ExpenseDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_details)
        text.text = "Expense: " + intent.getStringExtra("id")
    }
}
