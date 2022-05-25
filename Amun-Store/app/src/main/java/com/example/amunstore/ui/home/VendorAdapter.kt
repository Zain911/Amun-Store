package com.example.amunstore.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.amunstore.R
import com.example.amunstore.databinding.ItemBrandsBinding
import com.example.example.SmartCollections

class VendorAdapter(
    var vendorList: MutableList<SmartCollections>,
    private val onVendorClicked: (SmartCollections) -> Unit
) :
    RecyclerView.Adapter<VendorAdapter.VendorViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun changeList(list: MutableList<SmartCollections>) {
        vendorList.clear()
        vendorList = list
        notifyDataSetChanged()
    }

    class VendorViewHolder(var view: ItemBrandsBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VendorViewHolder {
        val binding =
            ItemBrandsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return VendorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VendorViewHolder, position: Int) {
        val item = vendorList[position]
        Glide.with(holder.view.brandImageView.context)
            .load(vendorList[position].image?.src)
            .placeholder(R.drawable.tshirt)
            .into(holder.view.brandImageView)

        holder.view.brandNameTextView.text = vendorList[position].title
        holder.view.root.setOnClickListener { onVendorClicked(item) }
    }

    override fun getItemCount() =
        vendorList.size

}