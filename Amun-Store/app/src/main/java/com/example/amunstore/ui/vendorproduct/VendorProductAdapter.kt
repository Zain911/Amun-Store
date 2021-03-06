package com.example.amunstore.ui.vendorproduct

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.amunstore.R
import com.example.amunstore.databinding.ItemCategoryProductBinding
import com.example.amunstore.data.model.product.Product

class VendorProductAdapter(
    private var productList: MutableList<Product>,
    val addProductToFavourite: (Product) -> Unit,
    val removeProductFromFavourite: (Product) -> Unit,
    val navigation:(Product)->Unit
) :
    RecyclerView.Adapter<VendorProductAdapter.ProductViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun changeList(newList: MutableList<Product>) {
        productList.clear()
        productList = newList
        notifyDataSetChanged()
    }

    class ProductViewHolder(var view: ItemCategoryProductBinding) :
        RecyclerView.ViewHolder(view.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            ItemCategoryProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)


        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.view.productNameTextView.text = productList[position].title
        holder.view.favouriteButtonImageView.visibility= View.GONE
        Glide.with(holder.view.productImageView.context)
            .load(productList[position].image?.src)
            .placeholder(R.drawable.tshirt)
            .into(holder.view.productImageView)
        if (productList[position].isFavourite) {
            holder.view.favouriteButtonImageView.setImageResource(R.drawable.ic_baseline_favorite_24)
            holder.view.favouriteButtonImageView.setColorFilter(R.color.darkRed)
        } else {
            holder.view.favouriteButtonImageView.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            holder.view.favouriteButtonImageView.setColorFilter(R.color.black)
        }

        holder.view.favouriteButtonImageView.setOnClickListener {
            if (productList[position].isFavourite) {
                productList[position].isFavourite = false
                removeProductFromFavourite(productList[position])
            } else {
                productList[position].isFavourite = true
                addProductToFavourite(productList[position])
            }
            notifyItemChanged(position)
        }
        holder.view.root.setOnClickListener { navigation(productList[position]) }
    }

    override fun getItemCount() = productList.size


}