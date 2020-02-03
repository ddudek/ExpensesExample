package pl.ddudek.mvxrnexample.networking.schema.responses


import com.google.gson.annotations.SerializedName

data class UserSchema(
    @SerializedName("email") val email: String,
    @SerializedName("first") val first: String,
    @SerializedName("last") val last: String
)