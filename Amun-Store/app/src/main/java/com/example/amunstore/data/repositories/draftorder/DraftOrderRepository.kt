package com.example.amunstore.data.repositories.draftorder

import com.example.amunstore.data.model.draftorder.DraftOrder
import com.example.amunstore.data.model.draftorder.DraftOrderRequest
import com.example.amunstore.data.model.draftorder.DraftOrderResponse
import com.example.amunstore.data.network.NetworkServices
import retrofit2.Response
import javax.inject.Inject

class DraftOrderRepository @Inject constructor(val networkServices: NetworkServices) :
    DraftOrderRepositoryInterface {

    override suspend fun updateDraftOrder(
        draftOrderId: String,
        draftOrderRequest: DraftOrderRequest
    ): Response<DraftOrderResponse> =
        networkServices.updateDraftOrder(draftOrderId, draftOrderRequest)

    override suspend fun createDraftOrder(draftOrderRequest: DraftOrderRequest) =
        networkServices.createDraftOrder(draftOrderRequest)

    override suspend fun getDraftOrderById(draftOrderId: String) =
        networkServices.getDraftOrderById(draftOrderId)


}