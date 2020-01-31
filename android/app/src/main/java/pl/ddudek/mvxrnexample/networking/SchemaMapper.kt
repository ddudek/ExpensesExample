package pl.ddudek.mvxrnexample.networking

import pl.ddudek.mvxrnexample.model.Amount
import pl.ddudek.mvxrnexample.model.Expense
import pl.ddudek.mvxrnexample.model.User
import pl.ddudek.mvxrnexample.networking.schema.AmountSchema
import pl.ddudek.mvxrnexample.networking.schema.ExpenseSchema
import pl.ddudek.mvxrnexample.networking.schema.UserSchema

fun ExpenseSchema.mapToModel(): Expense {
    return Expense(
            amount = this.amount.mapToModel(),
            category = this.category,
            comment = this.comment,
            date = this.date,
            id = this.id,
            merchant = this.merchant,
            receipts = this.receipts,
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
