package com.example.amunstore.ui.orders.orderdetails

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.amunstore.R
import com.example.amunstore.databinding.ItemLineItemBinding
import com.example.amunstore.data.model.order.LineItems
import com.example.amunstore.data.repositories.products.ProductsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class LineItemAdapter @Inject constructor(
    private val productsRepository: ProductsRepository,
    private var lineItemsList: MutableList<LineItems>
) :
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


        CoroutineScope(Dispatchers.Main).launch {
            val response = productsRepository.getProductImages(lineItemsList[position].productId!!)

            Log.d("ImagesResponse", lineItemsList[position].productId.toString())
            Log.d("ImagesResponse", response.body().toString())
            if (response.isSuccessful) {
                Glide
                    .with(holder.view.productImageView.context)
                    .load(response.body()?.imagesList?.get(position)?.src)
                    .placeholder(R.drawable.tshirt)
                    .into(holder.view.productImageView)
            }
        }

    }

    override fun getItemCount() = lineItemsList.size


}