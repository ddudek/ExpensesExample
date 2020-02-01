package pl.ddudek.mvxrnexample.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Amount(
    @SerializedName("currency") val currency: String,
    @SerializedName("value") val value: String
) : Parcelable