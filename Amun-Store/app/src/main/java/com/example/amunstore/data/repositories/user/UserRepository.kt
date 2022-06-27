package com.example.amunstore.data.repositories.user

import com.example.amunstore.data.model.address.AddAddressRequestModel
import com.example.amunstore.data.model.customer.CustomerResponse
import com.example.amunstore.data.model.customer.CustomerSingleResponse
import com.example.amunstore.data.model.customer.RequestCartDraftOrder
import com.example.amunstore.data.model.customer.RequestFavouriteDraftOrder
import com.example.amunstore.data.model.order.Customer
import com.example.amunstore.data.model.user.User
import com.example.amunstore.data.network.DraftOrderServices
import com.example.amunstore.data.network.NetworkServices
import com.example.amunstore.data.presistentstorage.sharedprefs.UserSharedPreferences
import com.example.amunstore.data.repositories.draftorder.DraftOrderRepository
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val networkServices: NetworkServices,
    private var sharedPref: UserSharedPreferences,
) : UserRepositoryInterface {

    override suspend fun addUserAddress(address: AddAddressRequestModel, customerId: Long) {

        val response = networkServices.addUserAddress(customerId.toString(), address)
        try {
            if (response.isSuccessful) {
                //     return "Done"

            } else {
                val jObjError =
                    JSONObject(response.errorBody()!!.toString()).getJSONObject("errors")
                //  return "Check Your Input"
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun setUserEmail(email: String) {
        sharedPref.setUserEmail(email)
    }

    override fun getUserEmail(): String = sharedPref.getUserEmail()

    override fun isUserLoggedIn(): Boolean {
        return sharedPref.getCustomerId() != -1L
    }

    override fun setUserLoggedIn(boolean: Boolean) {
        sharedPref.setUserLoggedIn(boolean)
    }


    override fun getUser(): User {
        TODO("Implement the return of user based on room or shared prefs")
    }

    override suspend fun getUserAddresses(customerId: Long) =
        networkServices.getUserAddresses(customerId)

    override fun getCustomerId() =
        sharedPref.getCustomerId()

    override fun setCustomerId(customerId: Long) {
        sharedPref.setCustomerId(customerId)
    }

    override fun getUserName() =
        sharedPref.getUserName()

    override fun setUserName(name: String) {
        sharedPref.setUserName(name)
    }

    override suspend fun createCustomer(customer: RequestBody): Response<Customer> {
        return networkServices.createCustomer(customer)
    }

    override suspend fun getUserByEmail(email: String): Response<CustomerResponse?> {
        return networkServices.getUserByEmail(email)
    }

    override suspend fun getUserEmailById(id: Long): Response<Customer> {
        return networkServices.getUserEmailById(id)
    }

    override fun getCartDraftOrderIdFromSharedPrefs(): String =
        sharedPref.getCartDraftOrderId()


    override fun setCartDraftOrderIdInSharedPrefs(id: String) {
        sharedPref.setCartDraftOrderId(id)
    }

    override fun setFavouriteDraftOrderIdInSharedPrefs(id: String) {
        sharedPref.setFavouriteOrderId(id)
    }

    override fun getFavouriteDraftOrderIdFromSharedPrefs(): String =
        sharedPref.getFavouriteOrderId()

    override suspend fun setUserFavouriteDraftOrderId(
        customerId: String,
        draftOrderId: RequestFavouriteDraftOrder,
    ) = networkServices.setFavouriteDraftOrdersId(
        customerId,
        draftOrderId
    )

    override suspend fun setUserCartDraftOrderId(
        customerId: String,
        draftOrderId: RequestCartDraftOrder,
    ) = networkServices.setCartDraftOrdersId(customerId, draftOrderId)

    override suspend fun deleteUserAddress(customerId: String, addressId: String) =
        networkServices.deleteUserAddress(customerId, addressId)

    override suspend fun setAddressAsDefault(customerId: String, addressId: String) {
        networkServices.setAddressAsDefault(customerId, addressId)
    }

}