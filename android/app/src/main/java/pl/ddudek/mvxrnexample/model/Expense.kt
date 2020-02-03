package pl.ddudek.mvxrnexample.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Expense(
        @SerializedName("id") val id: String,
        @SerializedName("amount") val amount: Amount,
        @SerializedName("category") val category: String,
        @SerializedName("comment") val comment: String,
        @SerializedName("date") val date: String,
        @SerializedName("merchant") val merchant: String,
        @SerializedName("receipts") val receipts: List<Receipt>,
        @SerializedName("user") val user: User
) : Parcelable