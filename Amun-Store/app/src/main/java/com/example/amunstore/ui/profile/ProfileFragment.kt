package com.example.amunstore.ui.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.amunstore.R
import com.example.amunstore.data.model.order.Order
import com.example.amunstore.data.model.product.Product
import com.example.amunstore.databinding.FragmentProfileBinding
import com.example.amunstore.ui.favourites.FavouriteListAdapter
import com.example.amunstore.ui.favourites.FavouritesFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var favouriteListAdapter: FavouriteListAdapter
    private lateinit var orderAdapter: OrdersAdapter


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        orderAdapter = OrdersAdapter(arrayListOf(), {}, {})
        favouriteListAdapter = FavouriteListAdapter(arrayListOf(), {
            viewModel.removeItemFromFavourites(it)
        }, {
            val action =
                it.id?.let { it1 ->
                    ProfileFragmentDirections.actionNavigationNotificationsToProductDetailsFragment(
                        it1)
                }
            view?.let { it1 ->
                action?.let { it2 ->
                    Navigation.findNavController(it1).navigate(it2)
                }
            }
        }
        )

        binding.ordersRecyclerView.adapter = orderAdapter
        binding.wishListRecyclerView.adapter = favouriteListAdapter

        viewModel.userName.observe(viewLifecycleOwner) {
            binding.userNameWelcomeTextView.text = resources.getString(R.string.welcome) + it
        }

        viewModel.ordersList.observe(viewLifecycleOwner) {
            if (it.size > 2)
                orderAdapter.changeList(it.subList(0, 2) as MutableList<Order>)
            else
                orderAdapter.changeList(it as MutableList<Order>)
        }

        viewModel.favProductList.observe(viewLifecycleOwner) {
            if (it.size > 4)
                favouriteListAdapter.changeList(it.subList(0, 4) as MutableList<Product>)
            else
                favouriteListAdapter.changeList(it as MutableList<Product>)
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

        binding.ordersMoreTextView.setOnClickListener {
            findNavController().navigate(R.id.ordersFragment)
        }

        /* binding.settingImageView.setOnClickListener{
             findNavController().navigate(R.id.settingsFragment)
         }
 */
        binding.backImageView.setOnClickListener { findNavController().popBackStack() }
        viewModel.isUserLoggedIn()

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