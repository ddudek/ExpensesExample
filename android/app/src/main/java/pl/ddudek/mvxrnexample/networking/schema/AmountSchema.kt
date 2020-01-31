package pl.ddudek.mvxrnexample.networking.schema


import com.google.gson.annotations.SerializedName

data class AmountSchema(
    @SerializedName("currency") val currency: String,
    @SerializedName("value") val value: String
)