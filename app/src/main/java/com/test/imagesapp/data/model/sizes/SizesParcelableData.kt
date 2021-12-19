package com.test.imagesapp.data.model.sizes

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SizesParcelableData(
    val photoId: String,
    val items: List<Size>
) : Parcelable