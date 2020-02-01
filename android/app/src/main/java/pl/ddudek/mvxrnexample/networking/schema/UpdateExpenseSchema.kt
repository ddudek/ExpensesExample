package pl.ddudek.mvxrnexample.networking.schema

import com.google.gson.annotations.SerializedName

data class UpdateExpenseSchema (
    @SerializedName("comment") val comment: String
)
