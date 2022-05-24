package com.example.amunstore.network

import com.example.example.CategoriesResponse
import retrofit2.Response
import retrofit2.http.GET

interface CategoryServices {


    //https://mobile-ismailia.myshopify.com/admin/api/2022-04/custom_collections.json
    @GET("custom_collections.json")
    suspend fun getCategories() : Response<CategoriesResponse>
}