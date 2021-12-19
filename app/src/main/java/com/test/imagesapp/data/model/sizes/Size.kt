package com.test.imagesapp.data.model.sizes

import android.os.Parcelable
import androidx.room.Ignore
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Size(
    val label: String,
    val height: Int,
    val width: Int,
    @SerializedName("source")
    val url: String
) : Parcelable {
    val labelEnum: Label
        get() = when (label) {
            "Thumbnail" -> Label.THUMBNAIL
            "Large Square" -> Label.SQUARE
            "Medium" -> Label.MEDIUM
            else -> Label.UNDEFINED
        }
}