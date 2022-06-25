package com.example.amunstore.data.repositories.draftorder

import com.example.amunstore.data.model.draftorder.DraftOrder
import com.example.amunstore.data.model.draftorder.DraftOrderRequest
import com.example.amunstore.data.model.draftorder.DraftOrderResponse
import retrofit2.Response

interface DraftOrderRepositoryInterface {

    suspend fun updateDraftOrder(
        draftOrderId: String,
        draftOrderRequest: DraftOrderRequest,
    ): Response<DraftOrderResponse>

    suspend fun createDraftOrder(draftOrderRequest: DraftOrderRequest): Response<DraftOrderResponse>

    suspend fun getDraftOrderById(draftOrderId: String): Response<DraftOrderResponse>
}