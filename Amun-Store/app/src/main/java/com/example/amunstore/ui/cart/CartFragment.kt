package com.example.amunstore.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.amunstore.data.model.cart.ItemCart
import com.example.amunstore.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartFragment : Fragment() {


    private val orderViewModel: CartViewModel by viewModels()

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var cartAdapter: CartAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val root: View = binding.root

        cartAdapter = CartAdapter(
            arrayListOf(),
            { orderViewModel.removeItem(it) },
            { orderViewModel.increaseItemQuantity(it) },
            { orderViewModel.decreaseItemQuantity(it) }
        )
        binding.recyclerView.adapter = cartAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            orderViewModel.getCartItems()
        }

        orderViewModel.data.observe(viewLifecycleOwner) {
            cartAdapter.changeList(it as MutableList<ItemCart>)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}