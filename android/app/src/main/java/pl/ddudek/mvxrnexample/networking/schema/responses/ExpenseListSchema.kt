package pl.ddudek.mvxrnexample.networking.schema.responses


import com.google.gson.annotations.SerializedName

data class ExpenseListSchema(
    @SerializedName("expenses") val expenses: List<ExpenseSchema>
)