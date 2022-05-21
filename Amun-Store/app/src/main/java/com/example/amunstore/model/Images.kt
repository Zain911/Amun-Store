package com.example.amunstore.model

import com.google.gson.annotations.SerializedName


data class Images (

  @SerializedName("id"                   ) var id                : Int?              = null,
  @SerializedName("product_id"           ) var productId         : Int?              = null,
  @SerializedName("position"             ) var position          : Int?              = null,
  @SerializedName("created_at"           ) var createdAt         : String?           = null,
  @SerializedName("updated_at"           ) var updatedAt         : String?           = null,
  @SerializedName("alt"                  ) var alt               : String?           = null,
  @SerializedName("width"                ) var width             : Int?              = null,
  @SerializedName("height"               ) var height            : Int?              = null,
  @SerializedName("src"                  ) var src               : String?           = null,
  @SerializedName("variant_ids"          ) var variantIds        : ArrayList<String> = arrayListOf(),
  @SerializedName("admin_graphql_api_id" ) var adminGraphqlApiId : String?           = null

)