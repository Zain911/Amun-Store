package com.example.amunstore.data.model.order

import com.google.gson.annotations.SerializedName


data class BillingAndShippingAddress (

    @SerializedName("first_name"    ) var firstName    : String? = null,
    @SerializedName("address1"      ) var address1     : String? = null,
    @SerializedName("phone"         ) var phone        : String? = null,
    @SerializedName("city"          ) var city         : String? = null,
    @SerializedName("zip"           ) var zip          : String? = null,
    @SerializedName("province"      ) var province     : String? = null,
    @SerializedName("country"       ) var country      : String? = null,
    @SerializedName("last_name"     ) var lastName     : String? = null,
    @SerializedName("address2"      ) var address2     : String? = null,
    @SerializedName("company"       ) var company      : String? = null,
    @SerializedName("latitude"      ) var latitude     : String? = null,
    @SerializedName("longitude"     ) var longitude    : String? = null,
    @SerializedName("name"          ) var name         : String? = null,
    @SerializedName("country_code"  ) var countryCode  : String? = null,
    @SerializedName("province_code" ) var provinceCode : String? = null

)