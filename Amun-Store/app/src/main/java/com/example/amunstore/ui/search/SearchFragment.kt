package com.example.amunstore.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.example.amunstore.R
import com.example.amunstore.databinding.FragmentSearchBinding
import com.example.amunstore.model.product.Product
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    var productFilter = ""
    private val binding get() = _binding!!
    val viewModel: SearchViewModel by viewModels()
    private lateinit var searchAdapter: SearchAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        intiFilterSpinner()

        binding.searchProductSearchView.requestFocus()
        lifecycle.coroutineScope.launch {
            viewModel.getProduct()
        }
        searchAdapter = SearchAdapter(arrayListOf()) {
            it
        }

        binding.itemsRecView.adapter = searchAdapter

        viewModel.filterList.observe(viewLifecycleOwner) {
            initView(it)
        }

        binding.searchProductSearchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchProduct(query, productFilter)
                Log.d( "onQueryTextSubmit: ",productFilter)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                viewModel.searchProduct(query, productFilter)
                Log.d( "onQueryTextSubmit: ",productFilter)

                return true
            }
        })
        binding.backImageView.setOnClickListener { requireActivity().onBackPressed() }


        return binding.root

    }

    private fun initView(productsList: List<Product>?) {
        if (productsList != null) {
            if (productsList.isEmpty()) {
                binding.emptyText.visibility = View.VISIBLE
                binding.emptyImg.visibility = View.VISIBLE
                searchAdapter.changeList(arrayListOf())
            } else {
                binding.emptyText.visibility = View.GONE
                binding.emptyImg.visibility = View.GONE
                searchAdapter.changeList(productsList.toMutableList())
            }
        }
    }

    private fun intiFilterSpinner() {
        binding.sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                when {
                    parent?.getItemAtPosition(position)!!.equals("Alphabetically") -> {
                        productFilter = "Alphabetically"

                    }
                    parent.getItemAtPosition(position).equals("price: Low to High") -> {
                        productFilter = "price: Low to High"
                    }
                    parent.getItemAtPosition(position).equals("price: High to Low") -> {
                        productFilter = "price: High to Low"
                    }
                    else -> {
                        productFilter = "none"
                    }

                }
                Log.d("onItemSelected: ",productFilter)
                viewModel.sortProducts(productFilter)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.sortProducts(productFilter)
              //  productFilter = "none"
            }
        }

    }

}
