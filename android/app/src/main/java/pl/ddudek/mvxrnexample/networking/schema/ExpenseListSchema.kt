package pl.ddudek.mvxrnexample.networking.schema


import com.google.gson.annotations.SerializedName

data class ExpenseListSchema(
    @SerializedName("expenses") val expenses: List<ExpenseSchema>
)