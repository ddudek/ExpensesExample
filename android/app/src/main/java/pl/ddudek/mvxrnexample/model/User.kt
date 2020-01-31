package pl.ddudek.mvxrnexample.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("email") val email: String,
    @SerializedName("first") val first: String,
    @SerializedName("last") val last: String
)