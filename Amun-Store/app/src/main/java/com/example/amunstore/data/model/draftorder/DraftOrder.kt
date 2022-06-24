package com.example.amunstore.data.model.draftorder

import com.example.amunstore.data.model.order.BillingAndShippingAddress
import com.example.amunstore.data.model.order.Customer
import com.example.amunstore.data.model.order.LineItems
import com.google.gson.annotations.SerializedName

data class DraftOrder(

    @SerializedName("id") var id: Long? = null,
    @SerializedName("order_id") var orderId: OrderId? = OrderId(),
    @SerializedName("name") var name: String? = null,
    @SerializedName("customer") var customer: Customer? = Customer(),
    @SerializedName("shipping_address") var shippingAddress: BillingAndShippingAddress? = BillingAndShippingAddress(),
    @SerializedName("billing_address") var billingAddress: BillingAndShippingAddress? = BillingAndShippingAddress(),
    @SerializedName("note") var note: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("currency") var currency: String? = null,
    @SerializedName("invoice_sent_at") var invoiceSentAt: String? = null,
    @SerializedName("invoice_url") var invoiceUrl: String? = null,
    @SerializedName("line_items") var lineItems: ArrayList<LineItems>? = arrayListOf(),
    @SerializedName("source_name") var sourceName: String? = null,
    @SerializedName("tags") var tags: String? = null,
    @SerializedName("tax_exempt") var taxExempt: Boolean? = null,
    @SerializedName("tax_exemptions") var taxExemptions: ArrayList<String> = arrayListOf(),
    @SerializedName("taxes_included") var taxesIncluded: Boolean? = null,
    @SerializedName("total_tax") var totalTax: String? = null,
    @SerializedName("subtotal_price") var subtotalPrice: Int? = null,
    @SerializedName("total_price") var totalPrice: String? = null,
    @SerializedName("completed_at") var completedAt: String? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null,
    @SerializedName("status") var status: String? = null,

    )

data class DraftOrderResponse(
    @SerializedName("draft_order") var draftOrder: DraftOrder,
)
