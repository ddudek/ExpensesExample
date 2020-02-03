package pl.ddudek.mvxrnexample.networking.schema.responses


import com.google.gson.annotations.SerializedName

data class ReceiptSchema(
    @SerializedName("url") val url: String
)