package com.example.amunstore.ui.favourites

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.amunstore.R
import com.example.amunstore.data.model.product.Product
import com.example.amunstore.databinding.ItemFavourite2Binding
import com.example.amunstore.databinding.ItemFavouriteBinding

class FavouriteListAdapter(
    private var favouriteList: MutableList<Product>,
    val removeProductFromFavourite: (Product) -> Unit,
    val itemDetailsClick: (Product) -> Unit
) :
    RecyclerView.Adapter<FavouriteListAdapter.ViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun changeList(list: MutableList<Product>) {
        favouriteList.clear()
        favouriteList = list
        notifyDataSetChanged()
    }

    class ViewHolder(var view: ItemFavourite2Binding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemFavourite2Binding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide
            .with(holder.view.productImageView.context)
            .load(favouriteList[position].imageSrc)
            .placeholder(R.drawable.tshirt)
            .into(holder.view.productImageView)

        holder.view.productNameTextView.text = favouriteList[position].title?.slice(0..15)

        holder.view.favouriteButtonImageView.setOnClickListener {
            removeProductFromFavourite(favouriteList[position])
        }

        holder.view.mainContainerCardView.setOnClickListener {
            itemDetailsClick(favouriteList[position])
        }
    }

    override fun getItemCount() = favouriteList.size
}