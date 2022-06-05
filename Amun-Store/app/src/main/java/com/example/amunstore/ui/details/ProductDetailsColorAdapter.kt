package com.example.amunstore.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.amunstore.R
import com.example.amunstore.data.model.details.ProductDetailsResponse

class ProductDetailsColorAdapter (private val arrayList: ProductDetailsResponse?) : RecyclerView.Adapter<ProductDetailsColorAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product_details_color, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(holder.imageView.context).load(arrayList?.product!!.images[position].src).into(holder.imageView)
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return arrayList?.product?.images!!.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        val imageView:ImageView = itemView.findViewById(R.id.item_product_color_image)

    }
}
