package com.example.amunstore.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.amunstore.data.model.order.Order
import com.example.amunstore.databinding.FragmentOrdersBinding
import com.example.amunstore.ui.profile.OrdersAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrdersFragment : Fragment() {

    private val viewModel: OrdersViewModel by viewModels()

    private var _binding: FragmentOrdersBinding? = null

    private val binding get() = _binding!!

    private lateinit var ordersAdapter: OrdersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        ordersAdapter = OrdersAdapter(arrayListOf()) {
            val action =
                it.id?.let { it1 ->
                    OrdersFragmentDirections.actionOrdersFragmentToOrderDetailsFragment(
                        it1
                    )
                }
            view?.let { it1 ->
                if (action != null) {
                    Navigation.findNavController(it1).navigate(action)
                }
            }
        }

        binding.ordersRecyclerView.adapter = ordersAdapter

        binding.ordersRecyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )

        viewModel.ordersList.observe(viewLifecycleOwner) {
            ordersAdapter.changeList(it as MutableList<Order>)
        }

        lifecycle.coroutineScope.launch {
            viewModel.getUserOrders()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}