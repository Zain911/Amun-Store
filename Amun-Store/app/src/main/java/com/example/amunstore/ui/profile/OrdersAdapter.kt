package com.example.amunstore.ui.profile

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amunstore.data.model.order.Order
import com.example.amunstore.databinding.ItemOrderInProfileBinding

class OrdersAdapter(
    private var ordersList: MutableList<Order>,
    private val itemClick: (Order) -> Unit
) :
    RecyclerView.Adapter<OrdersAdapter.ViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun changeList(newList: MutableList<Order>) {
        ordersList.clear()
        ordersList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemOrderInProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(var view: ItemOrderInProfileBinding) : RecyclerView.ViewHolder(view.root)

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.orderDateTextView.text = ordersList[position].createdAt
        holder.view.orderNameTextView.text = ordersList[position].name
        holder.view.orderTotalPrice.text =
            ordersList[position].totalPrice + " " + ordersList[position].currency

        holder.view.orderItemCountTextView.text =
            ordersList[position].lineItems.size.toString() + " Items"

        holder.view.mainConstrainLayout.setOnClickListener {
            itemClick(ordersList[position])
        }
    }


    override fun getItemCount() = ordersList.size
}
