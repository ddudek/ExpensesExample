package pl.ddudek.mvxrnexample.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Receipt(
        @SerializedName("url") val url: String
) : Parcelable