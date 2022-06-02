package com.example.amunstore.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.example.amunstore.data.model.product.Product
import com.example.amunstore.databinding.FragmentFavouritesBinding
import com.example.amunstore.ui.categories.singlecategory.CategoriesProductAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavouritesFragment : Fragment() {

    private val viewModel: FavouritesViewModel by viewModels()
    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var favouriteListAdapter: FavouriteListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        favouriteListAdapter = FavouriteListAdapter(arrayListOf()){
            viewModel.removeProductFromFavourite(it)
        }
        binding.favouritesRecyclerView.adapter = favouriteListAdapter

        lifecycle.coroutineScope.launch {
            viewModel.getFavourites()
        }

        viewModel.favList.observe(viewLifecycleOwner) {
            //favouriteListAdapter.changeList(it as MutableList<Product>)
        }

        viewModel.data.observe(viewLifecycleOwner) {
            favouriteListAdapter.changeList(it as MutableList<Product>)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}