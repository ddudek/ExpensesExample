package pl.ddudek.mvxrnexample.view.expenselist.mapper

import android.os.Bundle
import pl.ddudek.mvxrnexample.model.Amount
import pl.ddudek.mvxrnexample.model.Expense
import pl.ddudek.mvxrnexample.model.User


fun Expense.mapToBundle(): Bundle {
    val bundle = Bundle()
    bundle.putBundle("amount", amount.mapToBundle())
    bundle.putString("category", category)
    bundle.putString("comment", comment)
    bundle.putString("date", date)
    bundle.putString("id", id)
    bundle.putString("merchant", merchant)
    bundle.putSerializable("receipts", receipts.map { it.mapToBundle() }.toTypedArray())
    bundle.putBundle("user", user.mapToBundle())
    return bundle
}

fun User.mapToBundle(): Bundle? {
    val bundle = Bundle()
    bundle.putString("email", email)
    bundle.putString("first", first)
    bundle.putString("last", last)
    return bundle
}

fun Amount.mapToBundle(): Bundle {
    val bundle = Bundle()
    bundle.putString("currency", currency)
    bundle.putString("value", value)
    return bundle
}