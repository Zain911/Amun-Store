package com.example.amunstore.network

import com.example.amunstore.model.product.ProductsResponse
import com.example.example.CategoriesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkServices : CategoryServices , ProductServices