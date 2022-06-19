package com.example.amunstore.ui.orders.orderdetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.navArgs
import com.example.amunstore.databinding.FragmentOrderDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrderDetailsFragment : Fragment() {

    private val viewModel: OrderDetailsViewModel by viewModels()

    private var _binding: FragmentOrderDetailsBinding? = null

    private val binding get() = _binding!!

    private val args: OrderDetailsFragmentArgs by navArgs()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.order.observe(viewLifecycleOwner) {
            binding.orderIdTextView.text = "Order ID : " + it.id.toString()

            binding.shippingNameTextView.text =
                it.customer?.firstName + " " + it.customer?.lastName

            binding.phoneTextView.text = it.customer?.phone
            binding.phoneTextView.visibility = View.GONE
            binding.phoneImageView.visibility = View.GONE

            binding.mailTextView.text = it.email
            binding.locationTextView.text = it.customer?.defaultAddress?.address1

            binding.totalPaymentValueTextView.text = it.totalPrice + " " + it.currency
            binding.financialStateValueTextView.text =
                it.financialStatus?.replaceFirstChar { it1 -> it1.uppercase() }
            binding.numberOfItemsTextView.text = it.lineItems.size.toString() + " items"
            binding.orderLinesItemsRecyclerView.adapter = LineItemAdapter(it.lineItems)


            binding.orderDateTextView.text = it.createdAt?.substringBeforeLast("T")
        }

        lifecycle.coroutineScope.launch {
            viewModel.getOrderById(args.orderId)
        }

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}