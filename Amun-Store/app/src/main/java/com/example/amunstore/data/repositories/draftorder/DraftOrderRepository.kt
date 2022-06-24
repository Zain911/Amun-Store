package com.example.amunstore.data.repositories.draftorder

import com.example.amunstore.data.model.draftorder.DraftOrder
import com.example.amunstore.data.model.draftorder.DraftOrderRequest
import com.example.amunstore.data.network.NetworkServices
import retrofit2.Response
import javax.inject.Inject

class DraftOrderRepository @Inject constructor(val networkServices: NetworkServices) :
    DraftOrderRepositoryInterface {

    override suspend fun updateDraftOrder(
        draftOrderId: String,
        draftOrderRequest: DraftOrderRequest
    ): Response<DraftOrder> =
        networkServices.updateDraftOrder(draftOrderId, draftOrderRequest)

}