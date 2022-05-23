package com.example.amunstore.ui.categories

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.amunstore.R
import com.example.amunstore.databinding.ItemCategoryProductBinding
import com.example.amunstore.model.Products

class CategoriesProductAdapter(var productList: MutableList<Products>) :
    RecyclerView.Adapter<CategoriesProductAdapter.ProductViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun changeList(newList: MutableList<Products>) {
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
        Glide.with(holder.view.productImageView.context)
            .load(productList[position].image)
            .placeholder(R.drawable.tshirt)
            .into(holder.view.productImageView)
    }

    override fun getItemCount() = productList.size


}