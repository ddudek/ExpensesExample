package pl.ddudek.mvxrnexample.model


import com.google.gson.annotations.SerializedName

data class Amount(
    @SerializedName("currency") val currency: String,
    @SerializedName("value") val value: String
)