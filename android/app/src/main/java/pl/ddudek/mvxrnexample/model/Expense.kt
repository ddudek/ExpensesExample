package pl.ddudek.mvxrnexample.model


import com.google.gson.annotations.SerializedName

data class Expense(
        @SerializedName("id") val id: String,
        @SerializedName("amount") val amount: Amount,
        @SerializedName("category") val category: String,
        @SerializedName("comment") val comment: String,
        @SerializedName("date") val date: String,
        @SerializedName("merchant") val merchant: String,
        @SerializedName("receipts") val receipts: List<User>,
        @SerializedName("user") val user: User
)