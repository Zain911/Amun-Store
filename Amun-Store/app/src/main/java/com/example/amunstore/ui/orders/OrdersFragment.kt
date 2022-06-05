package com.example.amunstore.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.amunstore.databinding.FragmentFavouritesBinding
import com.example.amunstore.databinding.FragmentOrdersBinding

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersFragment : Fragment() {

    private val viewModel: OrdersViewModel by viewModels()
    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!

    private lateinit var ordersAdapter: OrdersListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        val root: View = binding.root
















        return root
    }


}