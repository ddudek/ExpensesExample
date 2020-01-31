package pl.ddudek.mvxrnexample.model


import com.google.gson.annotations.SerializedName

data class Expense(
        @SerializedName("amount") val amount: Amount,
        @SerializedName("category") val category: String,
        @SerializedName("comment") val comment: String,
        @SerializedName("date") val date: String,
        @SerializedName("id") val id: String,
        @SerializedName("merchant") val merchant: String,
        @SerializedName("receipts") val receipts: List<Any>,
        @SerializedName("user") val user: User
)