package com.example.amunstore.repository.categories

import com.example.amunstore.network.NetworkServices

class CategoriesRepository(
    private val networkServices: NetworkServices
) : CategoriesRepositoryInterface {

    override suspend fun getCategories() =
        networkServices.getCategories()

}