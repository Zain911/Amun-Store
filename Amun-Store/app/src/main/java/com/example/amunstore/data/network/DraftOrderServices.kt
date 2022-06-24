package com.example.amunstore.data.network

import com.example.amunstore.data.model.draftorder.DraftOrder
import com.example.amunstore.data.model.draftorder.DraftOrderRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface DraftOrderServices {


    @PUT("draft_orders/{draftOrder_id}.json")
    suspend fun updateDraftOrder(
        @Path("draftOrder_id") draftOrderId: String,
        @Body order: DraftOrderRequest
    ): Response<DraftOrder>
}