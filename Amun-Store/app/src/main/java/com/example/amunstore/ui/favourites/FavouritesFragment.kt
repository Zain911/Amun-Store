package com.example.amunstore.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.amunstore.data.model.product.Product
import com.example.amunstore.databinding.FragmentFavouritesBinding
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

        favouriteListAdapter = FavouriteListAdapter(arrayListOf(), {
            viewModel.removeProductFromFavourite(it)
        }, {

            val action =
                it.id?.let { it1 ->
                    FavouritesFragmentDirections.actionFavouriteFragmentToProductDetailsFragment(
                        it1
                    )
                }
            view?.let { it1 ->
                action?.let { it2 ->
                    Navigation.findNavController(it1).navigate(it2)
                }
            }
        })

        binding.favouritesRecyclerView.adapter = favouriteListAdapter
        binding.favouritesRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2).apply {

            }
        }

        lifecycle.coroutineScope.launch {
            viewModel.getFavourites()
        }

        viewModel.data.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                favouriteListAdapter.changeList(it as MutableList<Product>)
                binding.noDataGroup.visibility = View.GONE
            } else {
                binding.favouritesRecyclerView.visibility = View.GONE
                binding.noDataGroup.visibility = View.VISIBLE
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}