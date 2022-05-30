package com.example.example

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class SmartCollections (

  @SerializedName("id"                   ) var id                : Long?             = null,
  @SerializedName("handle"               ) var handle            : String?          = null,
  @SerializedName("title"                ) var title             : String?          = null,
  @SerializedName("sort_order"           ) var sortOrder         : String?          = null,
  @SerializedName("admin_graphql_api_id" ) var adminGraphqlApiId : String?          = null,
  @SerializedName("image"                ) var image             : Image?           = Image()

):Parcelable {
  constructor(parcel: Parcel) : this(
    parcel.readValue(Long::class.java.classLoader) as? Long,
    parcel.readString(),
    parcel.readString(),
    parcel.readString(),
    parcel.readString(),
    parcel.readParcelable(Image::class.java.classLoader)
  ) {
  }

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeValue(id)
    parcel.writeString(handle)
    parcel.writeString(title)
    parcel.writeString(sortOrder)
    parcel.writeString(adminGraphqlApiId)
    parcel.writeParcelable(image, flags)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<SmartCollections> {
    override fun createFromParcel(parcel: Parcel): SmartCollections {
      return SmartCollections(parcel)
    }

    override fun newArray(size: Int): Array<SmartCollections?> {
      return arrayOfNulls(size)
    }
  }
}

data class Image (

  @SerializedName("src"        ) var src       : String? = null

):Parcelable {
  constructor(parcel: Parcel) : this(parcel.readString()) {
  }

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeString(src)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<Image> {
    override fun createFromParcel(parcel: Parcel): Image {
      return Image(parcel)
    }

    override fun newArray(size: Int): Array<Image?> {
      return arrayOfNulls(size)
    }
  }
}