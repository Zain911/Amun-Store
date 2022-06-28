package com.example.amunstore.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.amunstore.data.model.order.Order
import com.example.amunstore.databinding.FragmentOrdersBinding
import com.example.amunstore.ui.profile.OrdersAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrdersFragment : Fragment() {

    private val viewModel: OrdersViewModel by viewModels()

    private var _binding: FragmentOrdersBinding? = null

    private val binding get() = _binding!!

    private lateinit var ordersAdapter: OrdersAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setUpSwiperRecyclerView()
        ordersAdapter = OrdersAdapter(arrayListOf(), {
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
        }, {

           lifecycleScope.launch { viewModel.removeOrder(it) }
        })


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

        binding.backImageView.setOnClickListener{
            findNavController().popBackStack()
        }

        return root
    }

    private fun setUpSwiperRecyclerView() {
        val itemTouchHelperCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                ordersAdapter.removeFromAdapter(viewHolder)
            }

        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.ordersRecyclerView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
      //  job.cancel()
    }


}