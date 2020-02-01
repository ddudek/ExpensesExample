package pl.ddudek.mvxrnexample.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName("email") val email: String,
    @SerializedName("first") val first: String,
    @SerializedName("last") val last: String
) : Parcelable