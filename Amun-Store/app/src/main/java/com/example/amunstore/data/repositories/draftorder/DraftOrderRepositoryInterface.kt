package com.example.amunstore.data.repositories.draftorder

import com.example.amunstore.data.model.draftorder.DraftOrder
import com.example.amunstore.data.model.draftorder.DraftOrderRequest
import retrofit2.Response

interface DraftOrderRepositoryInterface {

    suspend fun updateDraftOrder(
        draftOrderId: String,
        draftOrderRequest: DraftOrderRequest
    ): Response<DraftOrder>
}