package pl.ddudek.mvxrnexample.view.expenselist.mapper

import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.ReadableMap
import pl.ddudek.mvxrnexample.model.Amount
import pl.ddudek.mvxrnexample.model.Expense
import pl.ddudek.mvxrnexample.model.Receipt
import pl.ddudek.mvxrnexample.model.User


fun ReadableMap.mapToExpense(): Expense {
    val result = Expense(
            id = this.getString("id")!!,
            amount = this.getMap("amount")!!.mapToAmount(),
            category = this.getString("category")!!,
            comment = this.getString("comment")!!,
            date = this.getString("date")!!,
            merchant = this.getString("merchant")!!,
            receipts = this.getArray("receipts")!!.mapToReceiptList(),
            user = this.getMap("user")!!.mapToUser()
    )

    return result
}

fun ReadableMap.mapToUser(): User {
    val result = User(
            email = this.getString("email")!!,
            first = this.getString("first")!!,
            last = this.getString("last")!!
    )
    return result
}

fun ReadableMap.mapToAmount(): Amount {
    val result = Amount(
            currency = this.getString("currency")!!,
            value = this.getString("value")!!
    )
    return result
}

fun ReadableArray.mapToReceiptList(): List<Receipt> {
    val result = mutableListOf<Receipt>()
    for (i in 0 until this.size()) {
        val readableMap = this.getMap(i)
        readableMap?.let {
            result.add(it.mapToReceipt())
        }
    }
    return result
}

fun ReadableMap.mapToReceipt(): Receipt {
    val result = Receipt(
            url = this.getString("url")!!
    )
    return result
}