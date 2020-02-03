package pl.ddudek.mvxrnexample.networking

import pl.ddudek.mvxrnexample.model.Amount
import pl.ddudek.mvxrnexample.model.Expense
import pl.ddudek.mvxrnexample.model.Receipt
import pl.ddudek.mvxrnexample.model.User
import pl.ddudek.mvxrnexample.networking.schema.responses.AmountSchema
import pl.ddudek.mvxrnexample.networking.schema.responses.ExpenseSchema
import pl.ddudek.mvxrnexample.networking.schema.responses.ReceiptSchema
import pl.ddudek.mvxrnexample.networking.schema.responses.UserSchema

fun ExpenseSchema.mapToModel(): Expense {
    return Expense(
            id = this.id,
            amount = this.amount.mapToModel(),
            category = this.category,
            comment = this.comment,
            date = this.date,
            merchant = this.merchant,
            receipts = this.receipts.map { it.mapToModel() },
            user = this.user.mapToModel()
    )
}

fun UserSchema.mapToModel(): User {
    return User(email = this.email,
            first = this.first,
            last = this.last
    )
}

fun AmountSchema.mapToModel(): Amount {
    return Amount(
            currency = this.currency,
            value = this.value
    )
}

fun ReceiptSchema.mapToModel(): Receipt {
    return Receipt(
            url = this.url
    )
}
