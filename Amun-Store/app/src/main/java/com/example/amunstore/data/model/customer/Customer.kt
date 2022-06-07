package com.example.amunstore.data.model.customer

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import okhttp3.Address


data class Customer(
   @SerializedName("id")
   @Expose
 val id : Long? = null,
    val accepts_marketing: Boolean = false,
    val accepts_marketing_updated_at: String = " ",
    val addresses: List<Address>? = null,
    val admin_graphql_api_id: String = " ",
    val created_at: String = " ",
    val currency: String = "EGP",
    val email: String,
//    val email_marketing_consent: EmailMarketingConsent? = EmailMarketingConsent(),
    val first_name: String = " ",
    val last_name: String = " ",
    val last_order_id: String = " ",
    val last_order_name: String = " ",
    val marketing_opt_in_level: String? = null,
    val multipass_identifier: String? = null,
    val note: String = " ",
    val orders_count: Int = 0,
    val phone: String = " ",
    val sms_marketing_consent: Any? = null,
    val state: String? = " ",
    val tags: String? = " ",
    val tax_exempt: Boolean? = false,
    val tax_exemptions: List<String?>? = null,
    val total_spent: String = " ",
    val updated_at: String = " ",
    val verified_email: Boolean = true,
    val send_email_welcome: Boolean = false,

)