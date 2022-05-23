package com.example.amunstore.ui.categories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.amunstore.R
import com.example.amunstore.databinding.FragmentCategoriesBinding
import com.example.amunstore.model.Products
import com.google.android.material.tabs.TabLayout

class CategoriesFragment : Fragment() {


    private val viewModel: CategoriesViewModel by viewModels()

    private var _binding: FragmentCategoriesBinding? = null

    private val binding get() = _binding!!

    private lateinit var productsAdapter: CategoriesProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        productsAdapter = CategoriesProductAdapter(arrayListOf())
        binding.categoriesProductsRecyclerView.layoutManager = GridLayoutManager(context, 3)
        binding.categoriesProductsRecyclerView.adapter = productsAdapter


        //TODO remove the list it is just for testing and viewing products in the screen
        val mutableList = mutableListOf<Products>()
        mutableList.add(Products(title = "NewShirt"))
        mutableList.add(Products(title = "NewShirt"))
        mutableList.add(Products(title = "NewShirt"))
        mutableList.add(Products(title = "NewShirt"))
        mutableList.add(Products(title = "NewShirt"))
        mutableList.add(Products(title = "NewShirt"))
        productsAdapter.productList = mutableList

        viewModel.productList.observe(viewLifecycleOwner) {
            productsAdapter.productList = it as MutableList<Products>
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
