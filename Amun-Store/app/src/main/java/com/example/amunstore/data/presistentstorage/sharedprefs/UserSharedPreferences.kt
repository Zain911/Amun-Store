package com.example.amunstore.data.presistentstorage.sharedprefs

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSharedPreferences @Inject constructor(@ApplicationContext context: Context) :
    UserSharedPreferencesInterface {

    private val sharedPreferencesFileName = "SETTINGS"
    private val userEmailInSharedPreferences = "USER_EMAIL_IN_SHARED_PREFERENCES"
    private val userCustomerIdInSharedPreferences = "USER_CUSTOMER_ID_IN_SHARED_PREFERENCES"
    private val userNameInSharedPreferences = "USER_NAME_IN_SHARED_PREFERENCES"
    private val userCartDraftOrderInSharedPreferences = "CART_DRAFT_ORDER_ID_IN_SHARED_PREFERENCES"
    private val userFavouriteDraftOrder = "FAVOURITE_DRAFT_ORDER_ID_IN_SHARED_PREFERENCES"
    private val userLoggedInBoolean = "USER_LOGGED_IN_BOOLEAN"

    private val prefs =
        context.getSharedPreferences(sharedPreferencesFileName, Context.MODE_PRIVATE)


    override fun getUserEmail(): String {
        return prefs.getString(userEmailInSharedPreferences, "")!!
    }

    override fun setUserEmail(query: String) {
        prefs.edit().putString(userEmailInSharedPreferences, query).apply()
    }


    override fun getCustomerId() =
        prefs.getLong(userCustomerIdInSharedPreferences, -1L)

    override fun setCustomerId(customerId: Long) {
        prefs.edit().putLong(userCustomerIdInSharedPreferences, customerId).apply()
    }


    override fun getUserName() =
        prefs.getString(userNameInSharedPreferences, "N/A")!!

    override fun setUserName(name: String) {
        prefs.edit().putString(userNameInSharedPreferences, name).apply()
    }

    override fun setCartDraftOrderId(id: String) {
        prefs.edit().putString(userCartDraftOrderInSharedPreferences, id).apply()
    }

    override fun getCartDraftOrderId(): String =
        prefs.getString(userCartDraftOrderInSharedPreferences, "")!!

    override fun setFavouriteOrderId(id: String) {
        prefs.edit().putString(userFavouriteDraftOrder, id).apply()
    }

    override fun getFavouriteOrderId(): String=
        prefs.getString(userFavouriteDraftOrder,"")!!

    override fun setUserLoggedIn(boolean: Boolean) {
        prefs.edit().putBoolean(userLoggedInBoolean , boolean).apply()
    }


    fun clearAllCache() {
        prefs.edit().clear().apply()
    }


}