package com.radwan.products.data.remote.model.products

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Dimensions(

	@field:SerializedName("depth")
	val depth: Double? = null,

	@field:SerializedName("width")
	val width: Double? = null,

	@field:SerializedName("height")
	val height: Double? = null
) : Parcelable