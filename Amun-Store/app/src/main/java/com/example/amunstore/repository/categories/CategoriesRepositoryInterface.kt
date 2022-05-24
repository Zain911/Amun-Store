package com.example.amunstore.repository.categories

import com.example.example.CategoriesResponse
import retrofit2.Response

interface CategoriesRepositoryInterface {

    suspend fun getCategories(): Response<CategoriesResponse>

}