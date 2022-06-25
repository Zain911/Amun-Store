package com.example.amunstore.data.network

import com.example.amunstore.data.model.draftorder.DraftOrder
import com.example.amunstore.data.model.draftorder.DraftOrderRequest
import com.example.amunstore.data.model.draftorder.DraftOrderResponse
import retrofit2.Response
import retrofit2.http.*

interface DraftOrderServices {


    @PUT("draft_orders/{draftOrder_id}.json")
    suspend fun updateDraftOrder(
        @Path("draftOrder_id") draftOrderId: String,
        @Body order: DraftOrderRequest,
    ): Response<DraftOrderResponse>

    @POST("draft_orders.json")
    suspend fun createDraftOrder(@Body order: DraftOrderRequest): Response<DraftOrderResponse>

    @GET("draft_orders/{draftOrder_id}.json")
    suspend fun getDraftOrderById(@Path("draftOrder_id") draftOrderId: String) : Response<DraftOrderResponse>
}