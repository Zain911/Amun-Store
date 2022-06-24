package com.example.amunstore.data.presistentstorage.sharedprefs

interface UserSharedPreferencesInterface {

    fun getUserEmail(): String
    fun setUserEmail(query: String)

    fun getCustomerId(): Long
    fun setCustomerId(customerId: Long)

    fun getUserName(): String
    fun setUserName(name: String)

    fun setCartDraftOrderId(id: String)
    fun getCartDraftOrderId(): String

}