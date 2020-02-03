package pl.ddudek.mvxrnexample.networking.schema.responses


import com.google.gson.annotations.SerializedName

data class ExpenseSchema(
        @SerializedName("amount") val amount: AmountSchema,
        @SerializedName("category") val category: String,
        @SerializedName("comment") val comment: String,
        @SerializedName("date") val date: String,
        @SerializedName("id") val id: String,
        @SerializedName("merchant") val merchant: String,
        @SerializedName("receipts") val receipts: List<ReceiptSchema>,
        @SerializedName("user") val user: UserSchema
)