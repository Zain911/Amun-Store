package com.example.example

import com.google.gson.annotations.SerializedName


data class SmartCollections (

  @SerializedName("id"                   ) var id                : Long?             = null,
  @SerializedName("handle"               ) var handle            : String?          = null,
  @SerializedName("title"                ) var title             : String?          = null,
  @SerializedName("sort_order"           ) var sortOrder         : String?          = null,
  @SerializedName("admin_graphql_api_id" ) var adminGraphqlApiId : String?          = null,
  @SerializedName("image"                ) var image             : Image?           = Image()

)
data class Image (

  @SerializedName("src"        ) var src       : String? = null

)