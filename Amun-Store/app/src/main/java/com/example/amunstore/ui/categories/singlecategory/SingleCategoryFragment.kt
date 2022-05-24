package com.example.amunstore.ui.categories.singlecategory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.amunstore.databinding.FragmentSingleCategoryBinding
import com.example.amunstore.model.product.Products
import com.example.example.CustomCollections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SingleCategoryFragment(private val category : CustomCollections) : Fragment() {

    private val viewModel: SingleCategoryFragmentViewModel by viewModels()

    private var _binding: FragmentSingleCategoryBinding? = null

    private val binding get() = _binding!!

    private lateinit var productsAdapter: CategoriesProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSingleCategoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        productsAdapter = CategoriesProductAdapter(arrayListOf())
        binding.categoriesProductsRecyclerView.layoutManager = GridLayoutManager(context, 3)
        binding.categoriesProductsRecyclerView.adapter = productsAdapter

        viewModel.productList.observe(viewLifecycleOwner) {
            productsAdapter.changeList(it as MutableList<Products>)
        }

        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getProductByCategory(category)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}