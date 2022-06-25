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
import androidx.navigation.Navigation
import com.example.amunstore.R
import com.example.amunstore.data.model.product.Product
import com.example.amunstore.databinding.FragmentSearchBinding
import com.example.amunstore.ui.cart.addresses.AddressesBottomSheetDialogFragment
import com.example.amunstore.ui.search.searchfiltersbottomsheet.SearchFiltersBottomSheetDialogFragment

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

        //intiFilterSpinner()

        binding.searchProductSearchView.requestFocus()
        lifecycle.coroutineScope.launch {
            viewModel.getProduct()
        }
        searchAdapter = SearchAdapter(arrayListOf()) {
            val action = it.id?.let { it1 ->
                SearchFragmentDirections.actionSearchFragmentToProductDetailsFragment(it1)
            }
            view?.let { it1 ->
                action?.let { it2 ->
                    Navigation.findNavController(it1).navigate(it2)
                }
            }

        }

        binding.itemsRecView.adapter = searchAdapter

        viewModel.filterList.observe(viewLifecycleOwner) {
            initView(it)
        }

        binding.searchProductSearchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchProduct(query.trim())
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                viewModel.searchProduct(query.trim())
                return true
            }
        })
        binding.backImageView.setOnClickListener { requireActivity().onBackPressed() }


        binding.searchFiltersImageView.setOnClickListener {
            val fragment = SearchFiltersBottomSheetDialogFragment(productFilter) {
                productFilter = it
                viewModel.sortProducts(productFilter)
            }
            fragment.show(childFragmentManager, "SearchFilters")
        }
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


}

