package com.example.amunstore.ui.details

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.amunstore.data.model.details.ProductDetailsResponse
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.amunstore.R


class ProductDetailsAdapter (private val arrayList: ProductDetailsResponse) : RecyclerView.Adapter<ProductDetailsAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_similar_products, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(holder.imageView.context).load(arrayList.product.image?.src).into(holder.imageView)
        holder.titles.text =  arrayList.product.options[position].name
        holder.price.text = arrayList.product.options[position].values[0]

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return arrayList.product.options.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
       val imageView:ImageView = itemView.findViewById(R.id.details_image)
        val titles:TextView = itemView.findViewById(R.id.details_name)
        val price:TextView = itemView.findViewById(R.id.details_price)
    }
}


