package com.example.amunstore.ui.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.amunstore.R
import com.example.amunstore.data.model.product.Product
import com.example.amunstore.databinding.ItemBrandsBinding



class SearchAdapter(
    private var productList: MutableList<Product>,
    val navigation: (Product) -> Unit
) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {


    @SuppressLint("NotifyDataSetChanged")
    fun changeList(list: MutableList<Product>) {
        productList.clear()
        productList = list
        notifyDataSetChanged()
    }
    class ViewHolder(var view: ItemBrandsBinding) :
        RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemBrandsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val item = productList[position]
        Glide.with(holder.view.brandImageView.context)
            .load(productList[position].image?.src)
            .placeholder(R.drawable.tshirt)
            .into(holder.view.brandImageView)

        holder.view.brandNameTextView.text = productList[position].title?.slice(0..13)
        holder.view.root.setOnClickListener { navigation(item) }
    }

    override fun getItemCount() = productList.size
}