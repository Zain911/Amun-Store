package com.example.amunstore.ui.orders.orderdetails

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amunstore.databinding.ItemLineItemBinding
import com.example.example.LineItems

class LineItemAdapter(private var lineItemsList: MutableList<LineItems>) :
    RecyclerView.Adapter<LineItemAdapter.ViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun changeList(newList: MutableList<LineItems>) {
        lineItemsList.clear()
        lineItemsList = newList
        notifyDataSetChanged()
    }

    class ViewHolder(var view: ItemLineItemBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemLineItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.vendorNameTextView.text = lineItemsList[position].vendor
        holder.view.productNameTextView.text = lineItemsList[position].name
        holder.view.productPriceTextView.text = lineItemsList[position].price
        holder.view.quantityValueTextView.text =
            lineItemsList[position].quantity.toString() + " " + "Items"

        val variantTitle = lineItemsList[position].variantTitle?.split("/")
        holder.view.sizeValueTextView.text = variantTitle?.get(0)?.trim()
        holder.view.colorValueTextView.text = variantTitle?.get(1)?.trim()?.replaceFirstChar {
            it.uppercase()
        }

    }

    override fun getItemCount() = lineItemsList.size


}