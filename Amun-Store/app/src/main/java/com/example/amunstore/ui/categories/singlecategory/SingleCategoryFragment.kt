package com.example.amunstore.ui.categories.singlecategory

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.amunstore.databinding.FragmentSingleCategoryBinding
import com.example.amunstore.data.model.product.Product
import com.example.example.CustomCollections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SingleCategoryFragment(private val category: CustomCollections) : Fragment() {

    private val viewModel: SingleCategoryViewModel by viewModels()

    private var _binding: FragmentSingleCategoryBinding? = null

    private val binding get() = _binding!!

    private lateinit var productsAdapter: CategoriesProductAdapter
    private lateinit var subCategoriesAdapter: SubCategoriesRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSingleCategoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        productsAdapter = CategoriesProductAdapter(arrayListOf(), {
            viewModel.addItemToFavourite(it)
        }, {
            viewModel.removeItemFromFavourite(it)
        })
        binding.categoriesProductsRecyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.categoriesProductsRecyclerView.adapter = productsAdapter

        subCategoriesAdapter = SubCategoriesRecyclerAdapter(viewModel.getSubCategories()) {
            viewModel.filterDataBasedOnSubCategory(it)
        }
        binding.subCategoryRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.subCategoryRecyclerView.adapter = subCategoriesAdapter

        viewModel.viewedList.observe(viewLifecycleOwner) {
            productsAdapter.changeList(it as MutableList<Product>)
            Log.d("ProductList", it.toString())
        }

        lifecycle.coroutineScope.launch {
            viewModel.getProductByCategory(category)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}