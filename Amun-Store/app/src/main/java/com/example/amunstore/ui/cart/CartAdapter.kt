package com.example.amunstore.ui.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.amunstore.R
import com.example.amunstore.data.model.cart.ItemCart
import com.example.amunstore.data.model.product.Product
import com.example.amunstore.databinding.ItemCartBinding
import com.example.amunstore.databinding.ItemFavouriteBinding

class CartAdapter(
    private var itemCartList: MutableList<ItemCart>,
    val removeProductFromFavourite: (ItemCart) -> Unit,
) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun changeList(list: MutableList<ItemCart>) {
        itemCartList.clear()
        itemCartList = list
        notifyDataSetChanged()
    }

    class ViewHolder(var view: ItemCartBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide
            .with(holder.view.itemCartImage.context)
            .load(itemCartList[position].src)
            .placeholder(R.drawable.tshirt)
            .into(holder.view.itemCartImage)

        holder.view.itemCartTitle.text = itemCartList[position].title
        holder.view.itemCartPrice.text = itemCartList[position].price
        holder.view.sizeTextView.text = itemCartList[position].size
        holder.view.itemCountText.text = itemCartList[position].item_number

        holder.view.removeBtnTextView.setOnClickListener {
            removeProductFromFavourite(itemCartList[position])
        }
    }

    override fun getItemCount() = itemCartList.size
}