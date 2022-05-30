package com.example.amunstore.data.repositories.categories

import com.example.amunstore.data.network.NetworkServices

class CategoriesRepository(
    private val networkServices: NetworkServices
) : CategoriesRepositoryInterface {

    override suspend fun getCategories() =
        networkServices.getCategories()

}