package com.example.amunstore.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.example.amunstore.R
import com.example.amunstore.databinding.FragmentSearchBinding
import com.example.amunstore.model.product.Products
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    val viewModel: SearchViewModel by viewModels()
    private lateinit var searchAdapter: SearchAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        binding.searchProductSearchView.requestFocus()
        lifecycle.coroutineScope.launch {
            viewModel.getProduct()
        }
        searchAdapter = SearchAdapter(arrayListOf()) {
            it
        }

        binding.itemsRecView.adapter = searchAdapter

        viewModel.filterList.observe(viewLifecycleOwner) {
            initView(it as MutableList<Products>)
        }

        binding.searchProductSearchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchProduct(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                viewModel.searchProduct(query)
                return true
            }
        })
binding.backImageView.setOnClickListener{requireActivity().onBackPressed()}


        return binding.root

    }

    private fun initView(productsList: MutableList<Products>) {
        if (productsList.isEmpty()) {
            binding.emptyText.visibility = View.VISIBLE
            binding.emptyImg.visibility = View.VISIBLE
            searchAdapter.changeList(productsList)
        } else {
            searchAdapter.changeList(productsList)
            binding.emptyText.visibility = View.GONE
            binding.emptyImg.visibility = View.GONE
        }
    }


}
