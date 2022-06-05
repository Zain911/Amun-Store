package com.example.amunstore.ui.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.example.amunstore.R
import com.example.amunstore.data.model.product.Product
import com.example.amunstore.databinding.FragmentProfileBinding
import com.example.amunstore.ui.favourites.FavouriteListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var favouriteListAdapter: FavouriteListAdapter


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        favouriteListAdapter = FavouriteListAdapter(arrayListOf()) {

        }

        binding.wishListRecyclerView.adapter = favouriteListAdapter


        viewModel.ordersList.observe(viewLifecycleOwner) {
            //add data to adapter
        }

        viewModel.favProductList.observe(viewLifecycleOwner) {
            var list = it
            if (it.size > 4)
                list = list.subList(0, 4)
            favouriteListAdapter.changeList(list as MutableList<Product>)
        }

        viewModel.userName.observe(viewLifecycleOwner) {
            binding.userNameWelcomeTextView.text = resources.getString(R.string.welcome) + it
        }

        viewModel.isUserLoggedIn.observe(viewLifecycleOwner) {
            if (it) {
                // if user logged in get data from room
                showUserOrdersAndWishListForLoggedUser()
                lifecycle.coroutineScope.launch {
                    viewModel.getUserFavouriteProducts()
                    viewModel.getUserOrders()
                }
            } else {
                hideUserOrdersAndWishListForGuestUser()
            }
        }

        lifecycle.coroutineScope.launch {
            viewModel.getUserFavouriteProducts()
        }


        //viewModel.isUserLoggedIn()
        return root
    }

    private fun hideUserOrdersAndWishListForGuestUser() {
        binding.userDataGroup.visibility = View.GONE
    }

    private fun showUserOrdersAndWishListForLoggedUser() {
        binding.userDataGroup.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}